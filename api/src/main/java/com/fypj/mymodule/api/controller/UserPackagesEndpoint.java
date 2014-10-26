package com.fypj.mymodule.api.controller;

import com.fypj.mymodule.api.model.User;
import com.fypj.mymodule.api.model.User_Packages;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import static com.fypj.mymodule.api.util.OfyService.ofy;

/**
 * Created by jess on 26-Oct-14.
 */
@Api(name = "insightsUserPackages",description = "API to view all user's packages",  version = "v1", namespace = @ApiNamespace(ownerDomain = "api.mymodule.fypj.com", ownerName = "api.mymodule.fypj.com", packagePath=""))

public class UserPackagesEndpoint {
    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(UserPackagesEndpoint.class.getName());
    // Make sure to add this endpoint to your web.xml file if this is a web application.

    public UserPackagesEndpoint() {

    }

    /**
     * Return a collection of User Packages
     *
     * @param count The number of User Packages
     * @return a list of User packages
     */
    @ApiMethod(name = "listUserPackages")
    public CollectionResponse<User_Packages> listUserPackages(@Nullable @Named("cursor") String cursorString,
                                             @Nullable @Named("count") Integer count) {

        Query<User_Packages> query = ofy().load().type(User_Packages.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<User_Packages> records = new ArrayList<User_Packages>();
        QueryResultIterator<User_Packages> iterator = query.iterator();
        int num = 0;
        while (iterator.hasNext()) {
            records.add(iterator.next());
            if (count != null) {
                num++;
                if (num == count) break;
            }
        }

        //Find the next cursor
        if (cursorString != null && cursorString != "") {
            Cursor cursor = iterator.getCursor();
            if (cursor != null) {
                cursorString = cursor.toWebSafeString();
            }
        }
        return CollectionResponse.<User_Packages>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    /**
     * This inserts a new <code>users</code> object.
     * @param user The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertUserPackages")
    public User_Packages insertUserPackages(User_Packages userPackages) throws ConflictException {
        //If if is not null, then check if it exists. If yes, throw an Exception
        //that it is already present
        if (userPackages.getNric() != null) {
            List<User_Packages> recordList = findRecord(userPackages.getNric());
            User_Packages record = null;
            for(int i=0;i<recordList.size();i++){
                if(recordList.get(i).getPackagesID() == userPackages.getPackagesID()){
                    record = recordList.get(i);
                    break;
                }
            }
            if (findRecord(userPackages.getNric()) != null) {
                throw new ConflictException("Object already exists");
            }
        }
        //Since our @Id field is a Long, Objectify will generate a unique value for us
        //when we use put
        ofy().save().entity(userPackages).now();
        return userPackages;
    }

    /**
     * This deletes an existing <code>users</code> object.
     * @param id The id of the object to be deleted.
     */
    @ApiMethod(name = "removeUserPackages")
    public void removeUserPackages(@Named("nric") String nric,@Named("id") Long packageID) throws NotFoundException {
        List<User_Packages> recordList = findRecord(nric);
        User_Packages record = null;
        for(int i=0;i<recordList.size();i++){
            if(recordList.get(i).getPackagesID() == packageID){
                record = recordList.get(i);
                break;
            }
        }
        if(record == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().delete().entity(record).now();
    }

    //Private method to retrieve a <code>users</code> record
    private List<User_Packages> findRecord(String nric) {
        return ofy().load().type(User_Packages.class).filterKey("=",nric).list();
        //or return ofy().load().type(Quote.class).filter("id",id).first.now();
    }
}
