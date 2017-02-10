/*
 * $Id$
 *
 * Copyright (c) 2015 WorldTicket A/S
 * All rights reserved.
 */


import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import dtu.huayu.zheng.PersistentHelper;
import dtu.huayu.zheng.entity.Document;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Yiran Li(Yli))  / WorldTicket A/S
 * @version $Revision$ $Date$ 10/02/2017
 */
public class DBtest {
    @Test
    public void testCreateFetchEditSaveDelete() {
        String userName = "c606a925-d29e-4a50-8f86-b746a5498a34-bluemix";
        String password = "ea42c4e684fb2a651f5461352c7b8d69fbbf699e4f0b85559d83e1859423f92d";
        Database database = PersistentHelper.getDB(userName, password, "sample_nosql_db", "c606a925-d29e-4a50-8f86-b746a5498a34-bluemix");
        //create
        Document document = new Document();
        document.name = "name";
        document.value = "value";
        Response response = database.save(document);
        //201 - Created
        Assert.assertEquals(response.getStatusCode(), 201);
        //fetch
        Document document1 = database.find(Document.class, response.getId());
        Assert.assertNotNull(document1);
        Assert.assertEquals(document1.name, "name");
        //edit
        document1.name = "name2";
        response = database.update(document1);
        document1 = database.find(Document.class, response.getId());
        Assert.assertNotNull(document1);
        Assert.assertEquals(document1.name, "name2");
        //delete
        response = database.remove(document1._id, document1._rev);
        boolean contains  = database.contains(response.getId());
        Assert.assertFalse(contains);
    }
}
