/**
 * 
 */
package fr.univnantes.atal;

/**
 * @author dev
 *
 */
import java.util.*;

import fr.univnantes.atal.model.*;
import fr.univnantes.atal.utilitaires.*;
import fr.univnantes.atal.source.*;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.*;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.UnauthorizedException;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.JsonObject;

@Api(name = Configuration.API_NAME,
        version = Configuration.API_VERSION,
        audiences = Configuration.API_AUDIENCES,
        clientIds = Configuration.API_CLIENTID,
        namespace =
        @ApiNamespace(
                ownerDomain = Configuration.OWNERDOMAIN,
                ownerName = Configuration.OWNERNAME,
                packagePath = Configuration.PACKAGE_PATH)
)
public class ExploreEndPoint {
    @ApiMethod(name = "users", httpMethod = HttpMethod.GET)
    public CollectionResponse<Entity> users(User user, @Nullable @Named("keyword") String keyword,
                                            @Nullable @Named("next") String cursorString) throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Invalid credentials");
        }
        Query q = new Query(Profile.class.getCanonicalName())
                .setFilter(new Query.FilterPredicate("givenName",
                        Query.FilterOperator.GREATER_THAN_OR_EQUAL, keyword))
                .setFilter(new Query.FilterPredicate("givenName",
                        Query.FilterOperator.LESS_THAN, keyword + "\ufffd"))
                .setFilter(new Query.FilterPredicate("googleId",
                        Query.FilterOperator.NOT_EQUAL, user.getId()));
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery pq = datastore.prepare(q);

        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(Configuration.FETCH_PROFILE_LIMIT);

        QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
        if (cursorString != null) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(cursorString));
            cursorString = results.getCursor().toWebSafeString();
        }


        return CollectionResponse.<Entity>builder().setItems(results).setNextPageToken(cursorString).build();
    }
}