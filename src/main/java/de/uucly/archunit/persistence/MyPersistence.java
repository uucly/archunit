package de.uucly.archunit.persistence;

import de.uucly.archunit.business.MyBusinessObject;
import de.uucly.archunit.persistence.repo.MyRepoEntity;
import de.uucly.archunit.persistence.repo.MyRepository;

public class MyPersistence {

    private final MyRepository myRepository = new MyRepository();

    public MyBusinessObject findObject(MyBusinessObject myServiceObject) {

        MyRepoEntity entity = myRepository.getEntity();
        return new MyBusinessObject();
    }
}
