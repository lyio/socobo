package models.produce;

import org.springframework.data.repository.CrudRepository;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public interface ProduceRepository extends CrudRepository<Produce, String> {

    public Produce findProduceByName(String name);
}
