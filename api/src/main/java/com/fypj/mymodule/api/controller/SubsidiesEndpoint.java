package com.fypj.mymodule.api.controller;

import com.fypj.mymodule.api.model.MedicalHistory;
import com.fypj.mymodule.api.model.Subsidies;
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
@Api(name = "insightsSubsidies",description = "API to view all subsidies",  version = "v1", namespace = @ApiNamespace(ownerDomain = "api.mymodule.fypj.com", ownerName = "api.mymodule.fypj.com", packagePath=""))
public class SubsidiesEndpoint {

    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(SubsidiesEndpoint.class.getName());
    // Make sure to add this endpoint to your web.xml file if this is a web application.

    public SubsidiesEndpoint() {

    }

    /**
     * Return a collection of Subsidies
     *
     * @param count The number of Subsidies
     * @return a list of Subsidies
     */
    @ApiMethod(name = "listSubsidies")
    public CollectionResponse<Subsidies> listSubsidies(@Nullable @Named("cursor") String cursorString,
                                                                   @Nullable @Named("count") Integer count) {

        Query<Subsidies> query = ofy().load().type(Subsidies.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<Subsidies> records = new ArrayList<Subsidies>();
        QueryResultIterator<Subsidies> iterator = query.iterator();
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
        return CollectionResponse.<Subsidies>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    /**
     * This inserts a new <code>Subsidies</code> object.
     * @param Subsidies The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertSubsidy")
    public Subsidies insertSubsidy(Subsidies subsidies) throws ConflictException {
        //If if is not null, then check if it exists. If yes, throw an Exception
        //that it is already present
        if (subsidies.getSubsidiesID() != null) {
            if (findRecord(subsidies.getSubsidiesID()) != null) {
                throw new ConflictException("Object already exists");
            }
        }
        //Since our @Id field is a Long, Objectify will generate a unique value for us
        //when we use put
        ofy().save().entity(subsidies).now();
        return subsidies;
    }

    /**
     * This updates an existing <code>Subsidies</code> object.
     * @param Subsidies The object to be added.
     * @return The object to be updated.
     */
    @ApiMethod(name = "updateSubsidy")
    public Subsidies updateSubsidy(Subsidies subsidies)throws NotFoundException {
        if (findRecord(subsidies.getSubsidiesID()) == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().save().entity(subsidies).now();
        return subsidies;
    }

    /**
     * This deletes an existing <code>Subsidy</code> object.
     * @param id The id of the object to be deleted.
     */
    @ApiMethod(name = "removeSubsidiesy")
    public void removeSubsidy(@Named("id") Long id) throws NotFoundException {
        Subsidies record = findRecord(id);
        if(record == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().delete().entity(record).now();
    }

    //Private method to retrieve a <code>Subsidies</code> record
    private Subsidies findRecord(Long id) {
        return ofy().load().type(Subsidies.class).id(id).now();
        //or return ofy().load().type(Quote.class).filter("id",id).first.now();
    }
}
