
> https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManager.html
> https://www.cnblogs.com/lone5wolf/p/10940827.html
> https://www.cnblogs.com/blog5277/p/10661096.html
> https://www.jianshu.com/p/ae08c18fcb37
> https://www.jianshu.com/p/9c35c6fefed8


> JDBC的batch参数对于大数据量的新增/更新操作来说，非常有用，可以提升批量操作的效率

```yaml
在配置文件里加入：
# org.hibernate.dialect.Dialect类中
# batch_size默认为org.hibernate.dialect.DEFAULT_BATCH_SIZE=15
spring.jpa.properties.hibernate.jdbc.batch_size=5000
spring.jpa.properties.hibernate.jdbc.batch_versioned_data=true
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates =true

```
# batch_size默认为org.hibernate.dialect.DEFAULT_BATCH_SIZE=15
如果希望每100次向数据库flush一次，但是这里flush的时候，hibernate会每15条往数据库写入一次；
hibernate会判断当前batch里面已经有多少条待执行的sql(batchPosition变量)，如果待执行的条数等于batchSize(就是hibernate.jdbc.batch_size)；
执行performExecution：statement.executeBatch()，这个是jdbc原生的语句；

```
private final static int BATCH_SIZE = 5000;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Collection<TaskLog> batchSave(Collection<TaskLog> collection) {
        Iterator<TaskLog> iterator = collection.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            entityManager.persist(iterator.next());
            index++;
            if (index % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        if (index % BATCH_SIZE != 0) {
            entityManager.flush();
            entityManager.clear();
        }
        return collection;
    }

    @Override
    @Transactional
    public Collection<TaskLog> batchUpdate(Collection<TaskLog> collection) {
        Iterator<TaskLog> iterator = collection.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            entityManager.merge(iterator.next());
            index++;
            if (index % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        if (index % BATCH_SIZE != 0) {
            entityManager.flush();
            entityManager.clear();
        }
        return collection;
    }
```


```bash

        //20000条数据
        //batchSize = 15
        //16:34:54.395 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --saveAll--end--cost--8882
        //16:34:58.971 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --batchSave--end--cost--4576
        //16:35:05.845 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --batchUpdate--end--cost--6865

        //batchSize = 5000
        //16:40:28.380 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --saveAll--end--cost--7375
        //16:40:33.184 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --batchSave--end--cost--4575
        //16:40:39.846 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --batchUpdate--end--cost--5784

        //50000条数据
        //batchSize = 15
        //13:31:06.112 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --saveAll--end--cost--13064
        //13:31:10.992 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --batchSave--end--cost--10477
        //13:31:17.073 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --batchUpdate--end--cost--12034

        //batchSize = 5000
        //16:42:59.736 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --saveAll--end--cost--12967
        //16:43:10.734 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --batchSave--end--cost--10337
        //16:43:23.943 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --batchUpdate--end--cost--11789

        //100000条数据
        //batchSize = 15
        //13:39:11.584 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --saveAll--end--cost--23443
        //13:39:32.227 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --batchSave--end--cost--20643
        //13:39:56.138 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --batchUpdate--end--cost--23897

        //batchSize = 5000
        //16:46:43.551 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --saveAll--end--cost--21904
        //16:47:05.393 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --batchSave--end--cost--19742
        //16:47:31.232 [main, ,] INFO  c.m.a.s.a.r.TaskLogRepositoryTest - --batchUpdate--end--cost--21288

        
```        