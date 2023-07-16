package com.irdaislakhuafa.garbagepickupapi.exceptions.gql;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.DataNotFound;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;

@Component
public class GraphQLDataNotFoundHandler extends DataFetcherExceptionResolverAdapter {

	@Override
	@Nullable
	protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
		if (ex instanceof DataNotFound) {
			return GraphQLError.newError()
					.errorType(ErrorType.BAD_REQUEST)
					.message(ex.getMessage())
					.path(env.getExecutionStepInfo().getPath())
					.location(env.getField().getSourceLocation())
					.build();
		}
		return super.resolveToSingleError(ex, env);
	}

}