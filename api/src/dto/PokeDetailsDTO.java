package dto;

import java.util.List;

public record PokeDetailsDTO(Integer id, String name, Double weight, List<TypeSlotDTO> types, List<StatSlotDTO> stats)  {
}
