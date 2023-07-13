package com.irdaislakhuafa.garbagepickupapi.exceptions.gql;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.JwtTokenExpired;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;

@Component
public class GraphQLJwtTokenExpiredHandler extends DataFetcherExceptionResolverAdapter {

	@Override
	@Nullable
	protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
		if (ex instanceof JwtTokenExpired) {
			return GraphqlErrorBuilder.newError()
					.errorType(ErrorType.BAD_REQUEST)
					.message(ex.getMessage())
					.path(env.getExecutionStepInfo().getPath())
					.location(env.getField().getSourceLocation())
					.build();
		}

		return super.resolveToSingleError(ex, env);
	}

}
