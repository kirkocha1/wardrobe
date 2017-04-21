package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractLookRepository;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

/**
 * Created by kirill on 21.04.17.
 */

public class LookRepository extends AbstractLookRepository {

    public LookRepository(StorIOSQLite storIOSQLite) {
        super(storIOSQLite);
    }
}
