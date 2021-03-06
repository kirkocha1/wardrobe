package com.kirill.kochnev.homewardrobe.db.models;

import android.provider.BaseColumns;

/**
 * Created by kirill on 03.04.17.
 */
/* Beacause of storio annotation processor it isn't possible to make a base class
   and store _id or createDate fields (they are common for all entities),
   so the only proper decision was to make common interface or write custom PUT, GET, DELETE resolvers
*/
public interface IDbModel extends BaseColumns {

    Long getId();

}
