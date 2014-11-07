package com.fypj.mymodule.api.controller;

import com.fypj.mymodule.api.model.Appointment;
import com.fypj.mymodule.api.model.Clinic;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import static com.fypj.mymodule.api.util.OfyService.ofy;

/**
 * Created by L33525 on 8/10/2014.
 */
@Api(name = "insightsAppointment",description = "API to view all appointment", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.mymodule.fypj.com", ownerName = "api.mymodule.fypj.com", packagePath=""))
public class AppointmentEndpoint {

    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(AppointmentEndpoint.class.getName());
    // Make sure to add this endpoint to your web.xml file if this is a web application.

    public AppointmentEndpoint() {
    }

    /**
     * Return a collection of Appointment
     *
     * @param count The number of Appointment
     * @return a list of Appointments
     */
    @ApiMethod(name = "listAppointment")
    public CollectionResponse<Appointment> listAppointments(@Nullable @Named("cursor") String cursorString,
                                                  @Nullable @Named("count") Integer count) {

        Query<Appointment> query = ofy().load().type(Appointment.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<Appointment> records = new ArrayList<Appointment>();
        QueryResultIterator<Appointment> iterator = query.iterator();
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
        return CollectionResponse.<Appointment>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    /**
     * This inserts a new <code>Appointment</code> object.
     * @param Appointment The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertAppointment")
    public Appointment insertAppointment(Appointment appointment) throws ConflictException {
        //If if is not null, then check if it exists. If yes, throw an Exception
        //that it is already present
        if (appointment.getAppointmentID() != null) {
            if (findRecord(appointment.getAppointmentID()) != null) {
                throw new ConflictException("Object already exists");
            }
        }
        //Since our @Id field is a Long, Objectify will generate a unique value for us
        //when we use put
        ofy().save().entity(appointment).now();
        return appointment;
    }

    /**
     * This updates an existing <code>Appointment</code> object.
     * @param Appointment The object to be added.
     * @return The object to be updated.
     */
    @ApiMethod(name = "updateAppointment")
    public Appointment updateAppointment(Appointment appointment)throws NotFoundException {
        if (findRecord(appointment.getAppointmentID()) == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().save().entity(appointment).now();
        return appointment;
    }

    /**
     * This deletes an existing <code>Appointment</code> object.
     * @param id The id of the object to be deleted.
     */
    @ApiMethod(name = "removeAppointment")
    public void removeAppointment(@Named("id") Long id) throws NotFoundException {
        Appointment record = findRecord(id);
        if(record == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().delete().entity(record).now();
    }

    //Private method to retrieve a <code>Appointment</code> record
    private Appointment findRecord(Long id) {
        return ofy().load().type(Appointment.class).id(id).now();
        //or return ofy().load().type(Quote.class).filter("id",id).first.now();
    }
}
