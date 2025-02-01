package muit.backend.converter;

import muit.backend.domain.entity.musical.Actor;
import muit.backend.domain.entity.musical.Casting;
import muit.backend.dto.castingDTO.CastingResponseDTO;
import org.apache.logging.log4j.util.Cast;

import java.util.ArrayList;
import java.util.List;

public class CastingConverter {

    public static CastingResponseDTO.CastingResultListDTO toCastingResultListDTO(List<Actor> actors, Casting casting) {
        List<CastingResponseDTO.ActorResultDTO> actorResultDTOs = actors.stream()
                .map(CastingConverter::toActorResultDTO).toList();

        return CastingResponseDTO.CastingResultListDTO.builder()
                .musicalId(casting.getMusical().getId())
                .roleName(casting.getRoleName())
                .actorList(actorResultDTOs)
                .build();
    }

    public static CastingResponseDTO.ActorResultDTO toActorResultDTO(Actor actor) {
        return CastingResponseDTO.ActorResultDTO.builder()
                .realName(actor.getName())
                .actorPic(actor.getPicture())
                .build();
    }
}
