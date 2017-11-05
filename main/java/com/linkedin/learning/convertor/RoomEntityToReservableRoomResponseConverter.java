package com.linkedin.learning.convertor;

import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.model.Links;
import com.linkedin.learning.model.Self;
import com.linkedin.learning.model.response.ReservableRoomResponse;
import com.linkedin.learning.rest.ResourceConstants;
import org.springframework.core.convert.converter.Converter;

/*pageable list of rooms data model representing the database table*/
public class RoomEntityToReservableRoomResponseConverter implements Converter<RoomEntity, ReservableRoomResponse> {

    @Override /*keeps all the logic out of the controller*/
    public ReservableRoomResponse convert(RoomEntity source) {

        ReservableRoomResponse reservationResponse = new ReservableRoomResponse();
        reservationResponse.setRoomNumber(source.getRoomNumber());
        reservationResponse.setPrice( Integer.valueOf(source.getPrice()) ); /*perform conversion of string to integer*/

        /*instance of links to get resource by Id*/
        Links links = new Links();
        Self self = new Self();
        self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + source.getId()); /*get the url from the constants and add Id*/
        links.setSelf(self);

        reservationResponse.setLinks(links);

        return reservationResponse;
    }
}
