package datalayer;

import models.produce.Produce;
import org.springframework.data.repository.CrudRepository;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public interface ProduceRepository extends CrudRepository<Produce, String> {

    public Produce findProduceByName(String name);
}
