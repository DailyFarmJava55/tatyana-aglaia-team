package telran.dayli_farm.surprise_bag.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.dayli_farm.surprise_bag.model.Category;
import telran.dayli_farm.surprise_bag.model.Size;
import telran.dayli_farm.surprise_bag.model.SurpriseBag;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SurprisebagResponseDto {

	UUID id;

	Set<Size> size;

	Set<Category> category;

	String description;

	double price;

	int availableCount;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime pickupTimeStart;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime pickupTimeEnd;

	public static SurprisebagResponseDto of(SurpriseBag sb) {
		return SurprisebagResponseDto.builder()
				.id(sb.getId())
				.description(sb.getDescription())
				.price(sb.getPrice())
				.availableCount(sb.getAvailableCount())
				.category(sb.getCategory())
				.size(sb.getSize())
				.pickupTimeStart(sb.getPickupTimeStart())
				.pickupTimeEnd(sb.getPickupTimeEnd())
				.build();
	}

}
