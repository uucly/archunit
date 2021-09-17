package de.uucly.archunit.service;

import de.uucly.archunit.business.MyBusinessObject;
import de.uucly.archunit.persistence.MyPersistence;

public class MyControllerService {

    private final MyPersistence myPersistenceService = new MyPersistence();

    public MyBusinessObject getBusinessObject(MyBusinessObject myBusinessObject) {

        MyBusinessObject businessObject = myPersistenceService.findObject(myBusinessObject);
        return businessObject;
    }
}
