import {Card, Heading, Icon, Text} from "@chakra-ui/react";
import {HotelResponseDto} from "@api/models";
import { RiArrowRightSLine } from "react-icons/ri";
import {Button} from "@/components/ui/button";

type HotelCardProps = {
  item: HotelResponseDto
}

export default function HotelCard({
  item
}: HotelCardProps) {
  return (
    <Card.Root>
      <Card.Header>
        <Heading size="lg">
          {item.name}
        </Heading>
        <Card.Description>
          Hotel in {item.country!.name}
        </Card.Description>
      </Card.Header>
      <Card.Body>
        <Text fontSize="sm">
          {item.address}
        </Text>
      </Card.Body>
      <Card.Footer>
        <Button variant="plain" p={0}>
          View details
          <Icon>
            <RiArrowRightSLine />
          </Icon>
        </Button>
      </Card.Footer>
    </Card.Root>
  );
}
