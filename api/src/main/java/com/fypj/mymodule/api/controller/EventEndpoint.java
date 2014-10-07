package com.fypj.mymodule.api.controller;

/**
 * Created by L33525 on 7/10/2014.
 */

import com.fypj.mymodule.api.model.Event;
import com.fypj.mymodule.api.model.Quote;
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

/** An endpoint class we are exposing **/
@Api(name = "insightsEvent", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.mymodule.fypj.com", ownerName = "api.mymodule.fypj.com", packagePath=""))
public class EventEndpoint {

    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(EventEndpoint.class.getName());
    // Make sure to add this endpoint to your web.xml file if this is a web application.

    public EventEndpoint() {

    }

    /**
     * Return a collection of events
     *
     * @param count The number of events
     * @return a list of events
     */
    @ApiMethod(name = "listEvents")
    public CollectionResponse<Event> listEvents(@Nullable @Named("cursor") String cursorString,
                                               @Nullable @Named("count") Integer count) {

        Query<Event> query = ofy().load().type(Event.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<Event> records = new ArrayList<Event>();
        QueryResultIterator<Event> iterator = query.iterator();
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
        return CollectionResponse.<Event>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    /**
     * This inserts a new <code>events</code> object.
     * @param event The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertEvent")
    public Event insertEvent(Event event) throws ConflictException {
        //If if is not null, then check if it exists. If yes, throw an Exception
        //that it is already present
        if (event.getEventID() != null) {
            if (findRecord(event.getEventID()) != null) {
                throw new ConflictException("Object already exists");
            }
        }
        //Since our @Id field is a Long, Objectify will generate a unique value for us
        //when we use put
        ofy().save().entity(event).now();
        return event;
    }

    /**
     * This updates an existing <code>events</code> object.
     * @param event The object to be added.
     * @return The object to be updated.
     */
    @ApiMethod(name = "updateEvent")
    public Event updateEvent(Event event)throws NotFoundException {
        if (findRecord(event.getEventID()) == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().save().entity(event).now();
        return event;
    }

    /**
     * This deletes an existing <code>events</code> object.
     * @param id The id of the object to be deleted.
     */
    @ApiMethod(name = "removeEvent")
    public void removeEvent(@Named("id") Long id) throws NotFoundException {
        Event record = findRecord(id);
        if(record == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().delete().entity(record).now();
    }

    //Private method to retrieve a <code>events</code> record
    private Event findRecord(Long id) {
        return ofy().load().type(Event.class).id(id).now();
        //or return ofy().load().type(Quote.class).filter("id",id).first.now();
    }
}
