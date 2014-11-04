package com.fypj.mymodule.api.controller;

import com.fypj.mymodule.api.model.UserSubsidies;
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
@Api(name = "insightsUserSubsidies",description = "API to view all user's subsidies",  version = "v1", namespace = @ApiNamespace(ownerDomain = "api.mymodule.fypj.com", ownerName = "api.mymodule.fypj.com", packagePath=""))

public class UserSubsidiesEndpoint {
    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(UserSubsidiesEndpoint.class.getName());
    // Make sure to add this endpoint to your web.xml file if this is a web application.

    public UserSubsidiesEndpoint() {

    }

    /**
     * Return a collection of User Subsidies
     *
     * @param count The number of User Subsidies
     * @return a list of User Subsidies
     */
    @ApiMethod(name = "listUserSubsidies")
    public CollectionResponse<UserSubsidies> listUserSubsidies(@Nullable @Named("cursor") String cursorString,
                                             @Nullable @Named("count") Integer count) {

        Query<UserSubsidies> query = ofy().load().type(UserSubsidies.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<UserSubsidies> records = new ArrayList<UserSubsidies>();
        QueryResultIterator<UserSubsidies> iterator = query.iterator();
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
        return CollectionResponse.<UserSubsidies>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    /**
     * This inserts a new <code>users</code> object.
     * @param user The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertUserSubsidies")
    public UserSubsidies insertUserSubsidies(UserSubsidies userSubsidies) throws ConflictException {
        //If if is not null, then check if it exists. If yes, throw an Exception
        //that it is already present
        if (userSubsidies.getUserSubsidiesID() == null) {
            Query<UserSubsidies> query = ofy().load().type(UserSubsidies.class);
            List<UserSubsidies> records = new ArrayList<UserSubsidies>();
            QueryResultIterator<UserSubsidies> iterator = query.iterator();
            while (iterator.hasNext()) {
                records.add(iterator.next());
            }

            UserSubsidies foundRecord = null;
            for(int i=0;i<records.size();i++){
                if((records.get(i).getNric().equals(userSubsidies.getNric()) && (records.get(i).getSubsidiesID() == userSubsidies.getSubsidiesID()))){
                    foundRecord = records.get(i);
                    break;
                }
            }
            if (foundRecord != null) {
                throw new ConflictException("Object already exists");
            }
            else{
                ofy().save().entity(userSubsidies).now();
                return userSubsidies;
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
    @ApiMethod(name = "removeUserSubsidies")
    public void removeUserSubsidies(@Named("id") Long userSubsidiesID) throws NotFoundException {
        UserSubsidies record = findRecord(userSubsidiesID);
        if(record == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().delete().entity(record).now();
    }

    //Private method to retrieve a <code>users</code> record
    private UserSubsidies findRecord(Long userSubsidiesID) {
        return ofy().load().type(UserSubsidies.class).id(userSubsidiesID).now();
        //or return ofy().load().type(Quote.class).filter("id",id).first.now();
    }
}
