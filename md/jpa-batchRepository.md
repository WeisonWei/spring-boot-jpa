```java

import com.meishubao.app.base.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Iterator;

@Slf4j
public abstract class BatchRepository {

    private static final int BATCH_SIZE = 5000;

    @PersistenceContext
    private EntityManager entityManager;

    public <T extends BaseEntity> Iterable<T> batchSave(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
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
        return iterable;
    }

    public <T extends BaseEntity> Iterable<T> batchUpdate(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
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
        return iterable;
    }
}

```