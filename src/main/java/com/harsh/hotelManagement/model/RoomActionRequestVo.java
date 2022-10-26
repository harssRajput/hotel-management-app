package com.harsh.hotelManagement.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomActionRequestVo {
    @JsonAlias(value = {"hName", "hname"})
    String hotelName;

    String roomId;

    @JsonAlias(value = {"uname", "username"})
    String userName;
}
