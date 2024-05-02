package com.frobbery.gamification.ui.presenter.registry;

import com.frobbery.gamification.util.dto.RegistryDto;
import com.frobbery.gamification.ui.presenter.PresenterInput;

public interface RegistryPresenterInput extends PresenterInput<RegistryPresenterOutput> {

    void tryRegistry(RegistryDto bean);
}
