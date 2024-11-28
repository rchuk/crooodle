"use client"

import {Box, Flex, Heading, HStack, Icon, IconButton, Input, Text, VStack} from "@chakra-ui/react"
import {Button} from "@/components/ui/button";
import { RiMapPinLine, RiUser3Fill } from "react-icons/ri";
import {useRouter} from "next/navigation";
import WorldRegionsList from "@lib/components/world-region/world-regions-list";
import {useQueryState} from "nuqs";
import HotelList, {isHotelCriteriaPresent} from "@lib/components/hotel/hotel-list";
import {useMemo} from "react";
import {ListHotelsRequest} from "@api/apis";

export default function HomePage() {
  const router = useRouter();

  const [query, setQuery] = useQueryState("query", { defaultValue: "", clearOnDefault: true, throttleMs: 1000 });
  const [worldRegionId, setWorldRegionId] = useQueryState("world-region", { parse: parseInt, clearOnDefault: true });

  const hotelCriteria = useMemo<ListHotelsRequest>(() => (
    {
      query: query.trim().length !== 0 ? query.trim() : undefined,
      worldRegionId: worldRegionId ?? undefined
    }
  ), [query, worldRegionId])

  function clearFilters() {
    setQuery(null);
    setWorldRegionId(null);
  }

  function handleUserClick() {
    router.push("/login");
  }

  return (
    <VStack height="100vh">
      <Flex width="100%" flexDirection="column" alignItems="center" rowGap={10} p={5} backgroundColor="bg.subtle">
        <Flex width="100%">
          <Button
            variant="outline"
            onClick={clearFilters}
            visibility={isHotelCriteriaPresent(hotelCriteria) ? "visible" : "hidden"}
          >
            Clear filters
          </Button>
          <Box flex="1" />
          <IconButton rounded="full" size="sm" onClick={handleUserClick}>
            <RiUser3Fill />
          </IconButton>
        </Flex>
        <Flex alignItems="center" columnGap={2}>
          <Heading size="5xl" color="red.500">
            Crooodle
          </Heading>
          <Heading size="5xl">
             Find your next stay
          </Heading>
          <Icon size="2xl" color="red.500">
            <RiMapPinLine />
          </Icon>
        </Flex>
        <HStack gap={0} width="100%" maxW="lg">
          <Input
            borderTopRightRadius="0"
            borderBottomRightRadius="0"
            value={query}
            onChange={e => setQuery(e.target.value.trim())}
          />
          <Button borderTopLeftRadius="0" borderBottomLeftRadius="0">
            Search
          </Button>
        </HStack>
      </Flex>
      <Box width="100%" p={5}>
        {isHotelCriteriaPresent(hotelCriteria)
          ? <HotelList criteria={hotelCriteria} />
          : <WorldRegionsList onSelectWorldRegion={setWorldRegionId} />
        }
      </Box>
    </VStack>
  );
}
