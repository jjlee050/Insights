package com.fypj.mymodule.api.controller;

import com.fypj.mymodule.api.model.UserPackages;
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
    public CollectionResponse<UserPackages> listUserPackages(@Nullable @Named("cursor") String cursorString,
                                             @Nullable @Named("count") Integer count) {

        Query<UserPackages> query = ofy().load().type(UserPackages.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<UserPackages> records = new ArrayList<UserPackages>();
        QueryResultIterator<UserPackages> iterator = query.iterator();
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
        return CollectionResponse.<UserPackages>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    /**
     * This inserts a new <code>users</code> object.
     * @param user The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertUserPackages")
    public UserPackages insertUserPackages(UserPackages userPackages) throws ConflictException {
        //If if is not null, then check if it exists. If yes, throw an Exception
        //that it is already present
        if (userPackages.getUserPackagesID() == null) {
            Query<UserPackages> query = ofy().load().type(UserPackages.class);
            List<UserPackages> records = new ArrayList<UserPackages>();
            QueryResultIterator<UserPackages> iterator = query.iterator();
            while (iterator.hasNext()) {
                records.add(iterator.next());
            }

            UserPackages foundRecord = null;
            for(int i=0;i<records.size();i++){
                if((records.get(i).getNric().equals(userPackages.getNric()) && (records.get(i).getPackagesID() == userPackages.getPackagesID()))){
                    foundRecord = records.get(i);
                    break;
                }
            }
            if (foundRecord != null) {
                throw new ConflictException("Object already exists");
            }
            else{
                //Since our @Id field is a Long, Objectify will generate a unique value for us
                //when we use put
                ofy().save().entity(userPackages).now();
                return userPackages;
            }
        }
        else{
            return null;
        }
    }

    /**
     * This deletes an existing <code>users</code> object.
     * @param id The id of the object to be deleted.
     */
    @ApiMethod(name = "removeUserPackages")
    public void removeUserPackages(@Named("id") Long userPackageID) throws NotFoundException {
        UserPackages record = findRecord(userPackageID);
        if(record == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().delete().entity(record).now();
    }

    //Private method to retrieve a <code>users</code> record
    private UserPackages findRecord(Long userPackageID) {
        return ofy().load().type(UserPackages.class).id(userPackageID).now();
        //or return ofy().load().type(Quote.class).filter("id",id).first.now();
    }
}
