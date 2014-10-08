package com.fypj.mymodule.api.controller;

import com.fypj.mymodule.api.model.MedicalHistory;
import com.fypj.mymodule.api.model.User;
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
 * Created by L33525 on 8/10/2014.
 */
@Api(name = "insightsUser",description = "API to view all user",  version = "v1", namespace = @ApiNamespace(ownerDomain = "api.mymodule.fypj.com", ownerName = "api.mymodule.fypj.com", packagePath=""))
public class UserEndpoint {
    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(UserEndpoint.class.getName());
    // Make sure to add this endpoint to your web.xml file if this is a web application.

    public UserEndpoint() {

    }

    /**
     * Return a collection of User
     *
     * @param count The number of User
     * @return a list of User
     */
    @ApiMethod(name = "listUser")
    public CollectionResponse<User> listUser(@Nullable @Named("cursor") String cursorString,
                                                                   @Nullable @Named("count") Integer count) {

        Query<User> query = ofy().load().type(User.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<User> records = new ArrayList<User>();
        QueryResultIterator<User> iterator = query.iterator();
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
        return CollectionResponse.<User>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    /**
     * This inserts a new <code>users</code> object.
     * @param user The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertUser")
    public User insertUser(User user) throws ConflictException {
        //If if is not null, then check if it exists. If yes, throw an Exception
        //that it is already present
        if (user.getNric() != null) {
            if (findRecord(user.getNric()) != null) {
                throw new ConflictException("Object already exists");
            }
        }
        //Since our @Id field is a Long, Objectify will generate a unique value for us
        //when we use put
        ofy().save().entity(user).now();
        return user;
    }

    /**
     * This updates an existing <code>users</code> object.
     * @param user The object to be added.
     * @return The object to be updated.
     */
    @ApiMethod(name = "updateUser")
    public User updateUser(User user)throws NotFoundException {
        if (findRecord(user.getNric()) == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().save().entity(user).now();
        return user;
    }

    /**
     * This deletes an existing <code>users</code> object.
     * @param id The id of the object to be deleted.
     */
    @ApiMethod(name = "removeUser")
    public void removeUser(@Named("id") String nric) throws NotFoundException {
        User record = findRecord(nric);
        if(record == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().delete().entity(record).now();
    }

    //Private method to retrieve a <code>users</code> record
    private User findRecord(String nric) {
        return ofy().load().type(User.class).id(nric).now();
        //or return ofy().load().type(Quote.class).filter("id",id).first.now();
    }
}
