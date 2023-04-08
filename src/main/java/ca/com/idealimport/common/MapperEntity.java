package ca.com.idealimport.common;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
 //later use
@Mapper(componentModel = "spring")
public interface MapperEntity<S,R> {
    MapperEntity INSTANCE = Mappers.getMapper(MapperEntity.class);
    R dtoToEntity(S dto);

}
