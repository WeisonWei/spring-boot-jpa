

```java
@Async
    @Query(value = "select a from TaskLog as a, Task as b " +
            "where a.taskId = b.id and a.status = ?1 and a.updateDate = ?2 " +
            "and b.needFetch = true and b.isRepeatable = true order by a.id desc")
    CompletableFuture<List<TaskLog>> getAllRepeatableByStatusAndDate(TaskLog.STATUS status, String updateDate);
```

```java
@Transactional
    @Modifying
    @Async
    @Query(value = "delete from TaskLog as a " +
            "where a.status = ?1 and a.updateDate = ?2 " +
            "and exists (select 1 from Task as b where b.needFetch = true and b.isRepeatable = true and b.id = a.taskId)")
    CompletableFuture<Integer> deleteRepeatableByStatusAndDate(TaskLog.STATUS status, String updateDate);

会报这个错:
Caused by: org.springframework.dao.InvalidDataAccessApiUsageException: Modifying queries can only use void or int/Integer as return type!; 
nested exception is java.lang.IllegalArgumentException: Modifying queries can only use void or int/Integer as return type!

课修改为:

@Transactional
    @Modifying
    @Query(value = "delete from TaskLog as a " +
            "where a.status = ?1 and a.updateDate = ?2 " +
            "and exists (select 1 from Task as b where b.needFetch = true and b.isRepeatable = true and b.id = a.taskId)")
    Integer deleteRepeatableByStatusAndDate(TaskLog.STATUS status, String updateDate);

Integer deletedCount = CompletableFuture.supplyAsync(() -> taskLogRepository.deleteRepeatableByStatusAndDate(TaskLog.STATUS.ACTIVE, thatDayStr(1)))
                .join();
```