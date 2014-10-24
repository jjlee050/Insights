package com.fypj.mymodule.api.controller;

import com.fypj.mymodule.api.model.Packages;
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
@Api(name = "insightsPackages",description = "API to view all packages", version = "v1", namespace = @ApiNamespace(ownerDomain = "api.mymodule.fypj.com", ownerName = "api.mymodule.fypj.com", packagePath=""))
public class PackageEndpoint {

    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(PackageEndpoint.class.getName());
    // Make sure to add this endpoint to your web.xml file if this is a web application.

    public PackageEndpoint() {

    }

    /**
     * Return a collection of Package
     *
     * @param count The number of Package
     * @return a list of Packages
     */
    @ApiMethod(name = "listPackages")
    public CollectionResponse<Packages> listPackages(@Nullable @Named("cursor") String cursorString,
                                                                   @Nullable @Named("count") Integer count) {

        Query<Packages> query = ofy().load().type(Packages.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<Packages> records = new ArrayList<Packages>();
        QueryResultIterator<Packages> iterator = query.iterator();
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
        return CollectionResponse.<Packages>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    /**
     * This inserts a new <code>Package</code> object.
     * @param package The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertPackage")
    public Packages insertPackage(Packages packages) throws ConflictException {
        //If if is not null, then check if it exists. If yes, throw an Exception
        //that it is already present
        if (packages.getPackageID() != null) {
            if (findRecord(packages.getPackageID()) != null) {
                throw new ConflictException("Object already exists");
            }
        }
        //Since our @Id field is a Long, Objectify will generate a unique value for us
        //when we use put
        ofy().save().entity(packages).now();
        return packages;
    }

    /**
     * This updates an existing <code>Package</code> object.
     * @param package The object to be added.
     * @return The object to be updated.
     */
    @ApiMethod(name = "updatePackage")
    public Packages updatePackage(Packages packages)throws NotFoundException {
        if (findRecord(packages.getPackageID()) == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().save().entity(packages).now();
        return packages;
    }

    /**
     * This deletes an existing <code>Package</code> object.
     * @param id The id of the object to be deleted.
     */
    @ApiMethod(name = "removePackage")
    public void removePackage(@Named("id") Long id) throws NotFoundException {
        Packages record = findRecord(id);
        if(record == null) {
            throw new NotFoundException("Quote Record does not exist");
        }
        ofy().delete().entity(record).now();
    }

    //Private method to retrieve a <code>Package</code> record
    private Packages findRecord(Long id) {
        return ofy().load().type(Packages.class).id(id).now();
        //or return ofy().load().type(Quote.class).filter("id",id).first.now();
    }
}
