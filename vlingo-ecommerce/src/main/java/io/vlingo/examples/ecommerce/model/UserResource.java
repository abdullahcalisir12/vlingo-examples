package io.vlingo.examples.ecommerce.model;

import io.vlingo.common.Completes;
import io.vlingo.http.Response;
import io.vlingo.http.resource.ObjectResponse;
import io.vlingo.http.resource.Resource;

import static io.vlingo.http.resource.ResourceBuilder.get;
import static io.vlingo.http.resource.ResourceBuilder.resource;

public class UserResource {

    public static final String ROOT_URL = "/user";
    private final CartQuery cartQuery;

    public UserResource(final CartQuery cartQuery) {
        this.cartQuery = cartQuery;
    }

    public Resource<?> routes() {

        return resource("User resource fluent api",
                        get("/user/{userId}/cartSummary")
                                .param(Integer.class)
                                .handle(this::queryCartSummary));
    }

    private Completes<ObjectResponse<CartUserSummaryData>> queryCartSummary(Integer userId) {
       return cartQuery.getCartSummaryForUser(new UserId(userId))
               .andThenTo( data -> Completes.withSuccess(ObjectResponse.of(Response.Status.Ok, data)));
    }
}
