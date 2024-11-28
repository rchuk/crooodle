import {For, Grid, Heading} from "@chakra-ui/react";
import {WorldRegionResponseDto} from "@api/models";
import {useEffect, useState} from "react";
import useServices from "@lib/hooks/service-provider";
import WorldRegionCard from "@lib/components/world-region/world-region-card";

type WorldRegionsListProps = {
  onSelectWorldRegion: (id: number) => void
}

export default function WorldRegionsList({
  onSelectWorldRegion
}: WorldRegionsListProps) {
  const { worldRegionService } = useServices();
  const [worldRegions, setWorldRegions] = useState<WorldRegionResponseDto[] | null>(null);

  useEffect(() => {
    worldRegionService.listWorldRegions({ })
      .then(response => setWorldRegions(response.items ?? []))
      .catch();
  }, [worldRegionService]);

  return (
    <>
      <Heading size="3xl" mb={5} >
        Discover different world regions!
      </Heading>
      <Grid templateColumns="repeat(3, 1fr)" gap="6">
        <For each={worldRegions ?? []}>
          {item => (
            <WorldRegionCard key={item.id} item={item} onSelectWorldRegion={onSelectWorldRegion} />
          )}
        </For>
      </Grid>
    </>
  );
}
