package com.idn.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idn.backend.Model.Draft;
import com.idn.backend.Repo.DraftRepo;

@Service
public class DraftService {

    @Autowired
    private DraftRepo draftRepo;

    public Draft savePost(Draft draft) {
        return draftRepo.save(draft);
    }

}
