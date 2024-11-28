import {Flex, For, Grid, Heading, HStack, Icon} from "@chakra-ui/react";
import {HotelResponseDto} from "@api/models";
import {useEffect, useState} from "react";
import useServices from "@lib/hooks/service-provider";
import HotelCard from "@lib/components/hotel/hotel-card";
import {ListHotelsRequest} from "@api/apis";
import { RiEmotionSadLine } from "react-icons/ri";
import {
  PaginationItems,
  PaginationNextTrigger,
  PaginationPrevTrigger,
  PaginationRoot
} from "@/components/ui/pagination";

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

  const limit = 9;
  const [page, setPage] = useState<number>(1);
  const [hotels, setHotels] = useState<HotelResponseDto[] | null>(null);
  const [totalCount, setTotalCount] = useState<number | null>(null);

  useEffect(() => {
    hotelService.listHotels({
      ...criteria,
      page: (page - 1).toString(),
      limit: limit.toString()
    })
      .then(response => {
        setHotels(response.items!);
        setTotalCount(response.total!);
      })
      .catch();
  }, [hotelService, page]);

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
      {hotels !== null && hotels.length !== 0 && (
        <PaginationRoot
          count={totalCount ?? 0}
          pageSize={limit}
          page={page}
          onPageChange={e => setPage(e.page)}
          variant="solid"
          m={2}
        >
          <Flex justifyContent="center">
            <PaginationPrevTrigger />
            <PaginationItems />
            <PaginationNextTrigger />
          </Flex>
        </PaginationRoot>
      )}
    </>
  );
}
