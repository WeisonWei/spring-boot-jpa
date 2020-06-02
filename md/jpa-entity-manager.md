# EntityManager
## 1、JPA相关接口/类：Persistence
Persistence类是用于获取EntityManagerFactory实例。
该类包含一个名为 createEntityManagerFactory 的静态方法；
createEntityManagerFactory 方法有如下两个重载版本:
1.带有一个参数的方法以JPA配置文件persistence.xml中的持久化单元名为参数-->persistenceUnitName持久化单元名【与spring data 做集成的时候这个方法不使用】
2.带有两个参数的方法：前一个参数含义相同，后一个参数 Map类型，用于设置 JPA 的相关属性，这时将忽略其它地方设置的属性。Map 对象的属性名必须是 JPA 实现库提供商的名字空间约定的属性名。

## 2、EntityManagerFactory
EntityManagerFactory接口主要用来创建EntityManager实例；
该接口约定了如下4个方法：
1.createEntityManager()：用于创建实体管理器对象实例。
2.createEntityManager(Map map)：用于创建实体管理器对象实例的重载方法，Map 参数用于提供 EntityManager 的属性。
3.isOpen()：检查 EntityManagerFactory 是否处于打开状态。实体管理器工厂创建后一直处于打开状态，除非调用close()方法将其关闭。
4.close()：关闭 EntityManagerFactory 。 EntityManagerFactory 关闭后将释放所有资源，isOpen()方法测试将返回 false，其它方法将不能调用，否则将导致IllegalStateException异常。

## 3、EntityManager核心方法
### 3.1 find
find (Class<T> entityClass,Object primaryKey)：返回指定的 OID 对应的实体类对象，如果这个实体存在于当前的持久化环境，则返回一个被缓存的对象；
否则会创建一个新的 Entity, 并加载数据库中相关信息；
若OID不存在于数据库中，则返回一个 null。第一个参数为被查询的实体类类型，第二个参数为待查找实体的主键值。

### 3.2 getReference
getReference (Class<T> entityClass,Object primaryKey)：与find()方法类似，不同的是：如果缓存中不存在指定的 Entity, EntityManager 会创建一个 Entity 类的代理，
但是不会立即加载数据库中的信息，只有第一次真正使用此 Entity 的属性才加载，所以如果此 OID 在数据库不存在，getReference() 不会返回 null 值, 而是抛出EntityNotFoundException；

### 3.3 persistence
persist (Object entity)：用于将新创建的 Entity 纳入到 EntityManager 的管理。该方法执行后，传入 persist() 方法的 Entity 对象转换成持久化状态。
1.如果传入 persist() 方法的 Entity 对象已经处于持久化状态，则 persist() 方法什么都不做。
2.如果对删除状态的 Entity 进行 persist() 操作，会转换为持久化状态。
3.如果对游离状态的实体执行 persist() 操作，可能会在 persist() 方法抛出 EntityExistException(也有可能是在flush或事务提交后抛出)。

### 3.4 remove
remove (Object entity)：删除实例。如果实例是被管理的，即与数据库实体记录关联，则同时会删除关联的数据库记录，不能移除游离对象；

### 3.5 merge (T entity)：merge() 用于处理 Entity 的同步，即数据库的插入和更新操作；
#### 3.5.1 临时对象：会创建一个新的对象，把临时对象的属性复制到新的对象当中，然后对新的对象执行持久化操作，所以新的对象中有 id，但是以前的临时对象中没有id；
#### 3.5.2 游离对象：不会被持久化
若传入的是一个游离对象，即传入的对象有OID
1.若在entityManager 缓存中没有该对象
2.若在数据库中也没有对应的记录
3.JPA会创建一个新的对象，然后把当前游离对象的属性复制到新创建的对象中】
4.对新创建的对象执行 insert 操作
> 对象标识(Object identifier-OID):为了在系统中能够找到所需对象，我们需要为每一个对象分配一个唯一的表示号。在关系数据库中我们称之为关键字
#### 3.5.3 游离对象
若传入的是一个游离对象，即传入的对象有OID
1.若在entityManager 缓存中没有该对象
2.若在数据库中有对应的记录
3.JPA会查询对应的记录，然后返回该记录对应的对象，在然后会把游离对象的属性复制到查询到的对象中
4.对查询到的对象执行 update 操作
#### 3.5.4 游离对象
若传入的是一个游离对象，即传入的对象由OID
1.若在EntityManager 缓存中有对应的对象
2.JPA把游离对象的属性复制到EntityManager 缓存中的对象
3.EntityManager 缓存中的对象执行 update 操作
#### 3.5.5 总结
1.对比hibernate 中的 saveOrUpdate ,传入的是一个游离对象。
2.在 Customer customer2 = entityManager.find(Customer.class, 4);这一行跟EntityManager关联的还有持久化对象，他们的id是一样的，这个时候对对象进行merge方法 ，在里面会有一个复制的操作。
3.而hibernate 是行不通的，hibernate不允许 session在同一个时刻跟两个id相同的对象进行关联。
4.当然jpa也不可能，只不过entityManager 关联只有id是4的对象，但是这里面有复制的操作。

### 3.6 flush ()：同步持久上下文环境，即将持久上下文环境的所有未保存实体的状态信息保存到数据库中。
### 3.7 setFlushMode (FlushModeType flushMode)：设置持久上下文环境的Flush模式。
参数可以取2个枚举
FlushModeType.AUTO 为自动更新数据库实体，
FlushModeType.COMMIT 为直到提交事务时才更新数据库记录。
### 3.8 getFlushMode ()：获取持久上下文环境的Flush模式。返回FlushModeType类的枚举值。
### 3.9 refresh (Object entity)：用数据库实体记录的值更新实体对象的状态，即更新实例的属性值。
### 3.10 clear ()：清除持久上下文环境，断开所有关联的实体。如果这时还有未提交的更新则会被撤消。
### 3.11 contains (Object entity)：判断一个实例是否属于当前持久上下文环境管理的实体。
### 3.12 isOpen ()：判断当前的实体管理器是否是打开状态。
### 3.13 getTransaction ()：返回资源层的事务对象。EntityTransaction实例可以用于开始和提交多个事务
### 3.14 close ()：关闭实体管理器。之后若调用实体管理器实例的方法或其派生的查询对象的方法都将抛出 IllegalstateException  异常，除了 getTransaction 和 isOpen方法(返回 false)。不过，当与实体管理器关联的事务处于活动状态是，调用close 方法后持久上下文将仍处于被管理状态，直到事务完成。
### 3.15 createQuery (String qlString)： 创建查询对象
### 3.16 createNamedQuery (String name)：根据命名的查询语句块创建查询对象。参数为命名的查询语句、
### 3.17 createNativeQuery (String sqlString)：使用标准SQL语句创建查询对象，参数为标准SQL语句字符串。
### 3.18 createNativeQuery (String sqls, String resultSetMapping)： 使用标准SQL语句创建查询对象，并指定返回结果集Map 的名称。



## 4、EntityTransaction
EntityTransaction 接口用来管理资源层实体管理器的事务操作。

通过调用实体管理器的getTransaction方法 获得其实例。
1.begin () 用于启动一个事务，此后的多个数据库操作将作为整体被提交或撤消。若这时事务已启动则会抛出 IllegalStateException 异常。
2.commit () 用于提交当前事务。即将事务启动以后的所有数据库更新操作持久化至数据库中。
3.rollback () 撤消(回滚)当前事务。即撤消事务启动后的所有数据库更新操作，从而不对数据库产生影响。
4.setRollbackOnly () 使当前事务只能被撤消。
5.getRollbackOnly () 查看当前事务是否设置了只能撤消标志。
6.isActive () 查看当前事务是否是活动的。如果返回true则不能调用begin方法，否则将抛出 IllegalStateException 异常；如果返回 false 则不能调用 commit、rollback、setRollbackOnly 及 getRollbackOnly 方法，否则将抛出 IllegalStateException 异常。

需要注意的是：在JPA里面，先需要getTransaction ,在begin：

```
private EntityTransaction transaction;
    transaction = entityManager.getTransaction();
    transaction.begin();
```

在hibernate里面呢，直接begin，然后进行commit ：
```
    transaction = session.beginTransaction();
    transaction.commit();
```

## 5、总结：

在JPA规范中, EntityManager是完成持久化操作的核心对象。
实体作为普通Java对象，只有在调用EntityManager将其持久化后才会变成持久化对象。
EntityManager对象在一组实体类与底层数据源之间进行 O/R 映射的管理。
它可以用来管理和更新Entity Bean, 根椐主键查找Entity Bean, 还可以通过JPQL语句查询实体。

实体的状态:
1.新建状态:   新创建的对象，尚未拥有持久性主键。
2.持久化状态：已经拥有持久性主键并和持久化建立了上下文环境
3.游离状态：拥有持久化主键，但是没有与持久化建立上下文环境
4.删除状态:  拥有持久化主键，已经和持久化建立上下文环境，但是从数据库中删除。