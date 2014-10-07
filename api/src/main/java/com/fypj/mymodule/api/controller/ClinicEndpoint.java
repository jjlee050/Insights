package com.fypj.mymodule.api.controller;

/**
 * Created by L33525 on 7/10/2014.
 */

import com.fypj.mymodule.api.model.Clinic;
import com.fypj.mymodule.api.model.Event;
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
 * Created by L33525 on 7/10/2014.
 */
@Api(name = "insightsClinics", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.mymodule.fypj.com", ownerName = "api.mymodule.fypj.com", packagePath=""))
public class ClinicEndpoint {

    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(ClinicEndpoint.class.getName());
    // Make sure to add this endpoint to your web.xml file if this is a web application.

    public ClinicEndpoint() {

    }

    /**
     * Return a collection of clinic
     *
     * @param count The number of clinic
     * @return a list of clinics
     */
    @ApiMethod(name = "listClinics")
    public CollectionResponse<Clinic> listClinics(@Nullable @Named("cursor") String cursorString,
                                               @Nullable @Named("count") Integer count) {

        Query<Clinic> query = ofy().load().type(Clinic.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<Clinic> records = new ArrayList<Clinic>();
        QueryResultIterator<Clinic> iterator = query.iterator();
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
        return CollectionResponse.<Clinic>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    /**
     * This inserts a new <code>clinics</code> object.
     * @param clinic The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertClinic")
    public Clinic insertClinic(Clinic clinic) throws ConflictException {
        //If if is not null, then check if it exists. If yes, throw an Exception
        //that it is already present
        if (clinic.getClinicID() != null) {
            if (findRecord(clinic.getClinicID()) != null) {
                throw new ConflictException("Object already exists");
            }
        }
        //Since our @Id field is a Long, Objectify will generate a unique value for us
        //when we use put
        ofy().save().entity(clinic).now();
        return clinic;
    }

    /**
     * This updates an existing <code>clinics</code> object.
     * @param clinic The object to be added.
     * @return The object to be updated.
     */
    @ApiMethod(name = "updateClinic")
    public Clinic updateClinic(Clinic clinic)throws NotFoundException {
        if (findRecord(clinic.getClinicID()) == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().save().entity(clinic).now();
        return clinic;
    }

    /**
     * This deletes an existing <code>clinics</code> object.
     * @param id The id of the object to be deleted.
     */
    @ApiMethod(name = "removeClinic")
    public void removeClinic(@Named("id") Long id) throws NotFoundException {
        Clinic record = findRecord(id);
        if(record == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().delete().entity(record).now();
    }

    //Private method to retrieve a <code>clinics</code> record
    private Clinic findRecord(Long id) {
        return ofy().load().type(Clinic.class).id(id).now();
        //or return ofy().load().type(Quote.class).filter("id",id).first.now();
    }
}
