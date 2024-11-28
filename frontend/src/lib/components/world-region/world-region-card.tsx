import {Card, Heading, Icon} from "@chakra-ui/react";
import {WorldRegionResponseDto} from "@api/models";
import { RiArrowRightSLine } from "react-icons/ri";
import {Button} from "@/components/ui/button";

type WorldRegionCardProps = {
  item: WorldRegionResponseDto,
  onSelectWorldRegion: (id: number) => void
}


export default function WorldRegionCard({
  item,
  onSelectWorldRegion
}: WorldRegionCardProps) {
  return (
    <Card.Root>
      <Card.Header>
        <Heading size="lg">
          {item.name}
        </Heading>
      </Card.Header>
      <Card.Body />
      <Card.Footer>
        <Button variant="plain" p={0} onClick={_ => onSelectWorldRegion(item.id!)}>
          {item.countryCount} countries
          <Icon>
            <RiArrowRightSLine />
          </Icon>
        </Button>
      </Card.Footer>
    </Card.Root>
  );
}
