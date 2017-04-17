package com.bsuir.petition.service.petition.util.impl;

import com.bsuir.petition.bean.dto.category.CategoryListDTO;
import com.bsuir.petition.bean.dto.petition.PetitionDTO;
import com.bsuir.petition.bean.dto.petition.PetitionListDTO;
import com.bsuir.petition.bean.dto.petition.ShortPetitionDTO;
import com.bsuir.petition.bean.dto.user.UserDTO;
import com.bsuir.petition.bean.entity.Petition;
import com.bsuir.petition.service.category.util.DtoExchangerCategory;
import com.bsuir.petition.service.petition.util.PetitionDtoExchanger;
import com.bsuir.petition.service.petition.util.StatusExchanger;
import com.bsuir.petition.service.user.util.UserDtoExchanger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PetitionDtoExchangerImpl implements PetitionDtoExchanger {

    private StatusExchanger statusExchanger;

    private UserDtoExchanger userDtoExchanger;

    private DtoExchangerCategory dtoExchangerCategory;

    @Autowired
    public void setUserDtoExchanger(UserDtoExchanger userDtoExchanger) {
        this.userDtoExchanger = userDtoExchanger;
    }

    @Autowired
    public void setDtoExchangerCategory(DtoExchangerCategory dtoExchangerCategory) {
        this.dtoExchangerCategory = dtoExchangerCategory;
    }

    @Autowired
    public void setStatusExchanger(StatusExchanger statusExchanger) {
        this.statusExchanger = statusExchanger;
    }

    @Override
    public PetitionListDTO getPetitionListDTO(List<Petition> petitions) {
        PetitionListDTO petitionListDTO = new PetitionListDTO();
        ArrayList<ShortPetitionDTO> temp = petitionListDTO.getPetitions();
        for (Petition petition : petitions) {
            ShortPetitionDTO shortPetitionDTO = new ShortPetitionDTO();
            setShortPetitionDTO(shortPetitionDTO, petition);
            temp.add(shortPetitionDTO);
        }
        return petitionListDTO;
    }

    @Override
    public PetitionDTO getPetitionDTO(Petition petition) {
        PetitionDTO petitionDTO = new PetitionDTO();
        setShortPetitionDTO(petitionDTO, petition);
        petitionDTO.setExpiryDate(petition.getExpiryDate());

        UserDTO userDTO;
        userDTO = userDtoExchanger.getUserDTO(petition.getCreatedUser());
        petitionDTO.setUser(userDTO);

        return petitionDTO;
    }

    private void setShortPetitionDTO(ShortPetitionDTO shortPetitionDTO, Petition petition) {
        shortPetitionDTO.setName(petition.getName());
        shortPetitionDTO.setDescription(petition.getDescription());
        shortPetitionDTO.setNumberNecessaryVotes(petition.getNumberNecessaryVotes());
        shortPetitionDTO.setNumberVotes(petition.getVoteSet().size());

        CategoryListDTO categoryListDTO;
        categoryListDTO = dtoExchangerCategory.getCategoryListDTO(petition.getCategories());
        shortPetitionDTO.setCategories(categoryListDTO);

        String status;
        status = statusExchanger.getStatusName(petition.getStatusId());
        shortPetitionDTO.setStatus(status);
    }
}