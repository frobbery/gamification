package com.frobbery.gamification.ui.presenter.authorize;

import com.frobbery.gamification.dto.AuthorizeDto;
import com.frobbery.gamification.ui.presenter.PresenterInput;

public interface AuthorizePresenterInput extends PresenterInput<AuthorizePresenterOutput> {

    void tryAuthorize(AuthorizeDto bean);

}
