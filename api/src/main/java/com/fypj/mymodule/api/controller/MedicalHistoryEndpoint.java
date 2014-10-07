package com.fypj.mymodule.api.controller;

import com.fypj.mymodule.api.model.Clinic;
import com.fypj.mymodule.api.model.MedicalHistory;
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
@Api(name = "insightsMedicalHistory", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.mymodule.fypj.com", ownerName = "api.mymodule.fypj.com", packagePath=""))
public class MedicalHistoryEndpoint {

    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(MedicalHistoryEndpoint.class.getName());
    // Make sure to add this endpoint to your web.xml file if this is a web application.

    public MedicalHistoryEndpoint() {

    }

    /**
     * Return a collection of medical history
     *
     * @param count The number of medical history
     * @return a list of medical histories
     */
    @ApiMethod(name = "listMedicalHistories")
    public CollectionResponse<MedicalHistory> listMedicalHistories(@Nullable @Named("cursor") String cursorString,
                                                  @Nullable @Named("count") Integer count) {

        Query<MedicalHistory> query = ofy().load().type(MedicalHistory.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<MedicalHistory> records = new ArrayList<MedicalHistory>();
        QueryResultIterator<MedicalHistory> iterator = query.iterator();
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
        return CollectionResponse.<MedicalHistory>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    /**
     * This inserts a new <code>medicalHistories</code> object.
     * @param medicalHistory The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertMedicalHistory")
    public MedicalHistory insertMedicalHistory(MedicalHistory medicalHistory) throws ConflictException {
        //If if is not null, then check if it exists. If yes, throw an Exception
        //that it is already present
        if (medicalHistory.getMedicalHistoryID() != null) {
            if (findRecord(medicalHistory.getMedicalHistoryID()) != null) {
                throw new ConflictException("Object already exists");
            }
        }
        //Since our @Id field is a Long, Objectify will generate a unique value for us
        //when we use put
        ofy().save().entity(medicalHistory).now();
        return medicalHistory;
    }

    /**
     * This updates an existing <code>medicalHistories</code> object.
     * @param medicalHistory The object to be added.
     * @return The object to be updated.
     */
    @ApiMethod(name = "updateMedicalHistory")
    public MedicalHistory updateMedicalHistory(MedicalHistory medicalHistory)throws NotFoundException {
        if (findRecord(medicalHistory.getMedicalHistoryID()) == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().save().entity(medicalHistory).now();
        return medicalHistory;
    }

    /**
     * This deletes an existing <code>medicalHistories</code> object.
     * @param id The id of the object to be deleted.
     */
    @ApiMethod(name = "removeMedicalHistory")
    public void removeMedicalHistory(@Named("id") Long id) throws NotFoundException {
        MedicalHistory record = findRecord(id);
        if(record == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().delete().entity(record).now();
    }

    //Private method to retrieve a <code>clinics</code> record
    private MedicalHistory findRecord(Long id) {
        return ofy().load().type(MedicalHistory.class).id(id).now();
        //or return ofy().load().type(Quote.class).filter("id",id).first.now();
    }
}
