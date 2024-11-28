import {For, Grid, Heading, HStack, Icon} from "@chakra-ui/react";
import {HotelResponseDto} from "@api/models";
import {useEffect, useState} from "react";
import useServices from "@lib/hooks/service-provider";
import HotelCard from "@lib/components/hotel/hotel-card";
import {ListHotelsRequest} from "@api/apis";
import { RiEmotionSadLine } from "react-icons/ri";

type HotelListProps = {
  criteria: ListHotelsRequest
}

function fallback() {
  return (
    <HStack>
      <Heading>
        There are no hotels here yet
      </Heading>
      <Icon fontSize="2xl">
        <RiEmotionSadLine />
      </Icon>
    </HStack>
  );
}

export function isHotelCriteriaPresent(criteria: ListHotelsRequest) {
  const {
    query,
    worldRegionId,
    countryId
  } = criteria;

  return query !== undefined || worldRegionId !== undefined || countryId !== undefined;
}

export default function HotelList({
  criteria
}: HotelListProps) {
  const { hotelService } = useServices();
  const [hotels, setHotels] = useState<HotelResponseDto[] | null>(null);

  // TODO: Pagination
  useEffect(() => {
    hotelService.listHotels(criteria)
      .then(response => setHotels(response.items ?? []))
      .catch();
  }, [hotelService]);

  return (
    <>
      <Heading size="3xl" mb={5} >
        Discover hotels
      </Heading>
      <Grid templateColumns="repeat(3, 1fr)" gap="6">
        <For each={hotels ?? []} fallback={fallback()}>
          {item => (
            <HotelCard key={item.id} item={item} />
          )}
        </For>
      </Grid>
    </>
  );
}
