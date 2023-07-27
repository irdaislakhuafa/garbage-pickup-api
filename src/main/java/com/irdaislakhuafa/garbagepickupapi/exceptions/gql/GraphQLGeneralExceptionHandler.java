package com.irdaislakhuafa.garbagepickupapi.exceptions.gql;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.*;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class GraphQLGeneralExceptionHandler {
    @Component
    public static class BadRequest extends DataFetcherExceptionResolverAdapter {
        @Override
        protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
            final var accepted = (
                    ex instanceof InvalidPassword ||
                            ex instanceof JwtTokenExpired ||
                            ex instanceof DataNotFound ||
                            ex instanceof DataAlreadyExists ||
                            ex instanceof BadRequestException ||
                            ex instanceof AuthenticationException
            );

            if (accepted) {
                final var errors = GraphQLError.newError()
                        .errorType(ErrorType.BAD_REQUEST)
                        .message(ex.getMessage())
                        .path(env.getExecutionStepInfo().getPath())
                        .location(env.getField().getSourceLocation())
                        .build();
                return errors;
            }

            return super.resolveToSingleError(ex, env);
        }
    }
}