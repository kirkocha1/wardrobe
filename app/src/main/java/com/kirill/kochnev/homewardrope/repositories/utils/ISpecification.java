package com.kirill.kochnev.homewardrope.repositories.utils;

import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

/**
 * Created by kirill on 26.04.17.
 */

public interface ISpecification {

    boolean isRow();

    Query.CompleteBuilder getQueryStatement();

    RawQuery.CompleteBuilder getRawQueryStatement();

}
