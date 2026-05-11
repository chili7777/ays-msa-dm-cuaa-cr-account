package com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller;

import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.MovementCreateRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.MovementPatchRequestDto;
import com.pichincha.dm.cuaa.account.infrastructure.entrypoint.controller.entities.MovementUpdateRequestDto;
import com.pichincha.dm.cuaa.account.shared.RequestTestCase;
import com.pichincha.dm.cuaa.account.shared.objectmothers.HttpHeadersMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.JsonMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.MovementCreateRequestDtoMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.MovementPatchRequestDtoMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.MovementUpdateRequestDtoMother;
import com.pichincha.dm.cuaa.account.shared.objectmothers.UuidMother;
import org.junit.jupiter.api.Test;

class MovementsControllerTest extends RequestTestCase {

    @Test
    void given_validMovementCreateRequest_when_createMovement_then_returnCreatedStatus() throws Exception {
        MovementCreateRequestDto requestDto = MovementCreateRequestDtoMother.random();
        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("POST", "/movements", requestBody, 201, HttpHeadersMother.random());
    }

    @Test
    void given_accountId_when_listMovements_then_returnOkStatus() throws Exception {
        String accountId = UuidMother.random().toString();
        assertRequest("GET", "/movements?accountId=" + accountId, 200, HttpHeadersMother.random());
    }

    @Test
    void given_movementId_when_getMovementById_then_returnOkStatus() throws Exception {
        String movementId = UuidMother.random().toString();
        assertRequest("GET", "/movements/" + movementId, 200, HttpHeadersMother.random());
    }

    @Test
    void given_validMovementUpdateRequest_when_replaceMovement_then_returnNoContentStatus() throws Exception {
        String movementId = UuidMother.random().toString();
        MovementUpdateRequestDto requestDto = MovementUpdateRequestDtoMother.random();
        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("PUT", "/movements/" + movementId, requestBody, 204, HttpHeadersMother.random());
    }

    @Test
    void given_validMovementPatchRequest_when_patchMovement_then_returnNoContentStatus() throws Exception {
        String movementId = UuidMother.random().toString();
        MovementPatchRequestDto requestDto = MovementPatchRequestDtoMother.random();
        String requestBody = JsonMother.fromObject(requestDto);

        assertRequestWithBody("PATCH", "/movements/" + movementId, requestBody, 204, HttpHeadersMother.random());
    }

    @Test
    void given_movementId_when_deleteMovement_then_returnNoContentStatus() throws Exception {
        String movementId = UuidMother.random().toString();
        assertRequest("DELETE", "/movements/" + movementId, 204, HttpHeadersMother.random());
    }
}