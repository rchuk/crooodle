import {Flex, Heading, HStack, Icon, Input, Text, VStack} from "@chakra-ui/react"
import {Button} from "@/components/ui/button";
import { RiMapPinLine } from "react-icons/ri";

export default function HomePage() {
  return (
    <VStack height="100vh">
      <Flex width="100%" flexDirection="column" alignItems="center" rowGap={10} p={5} backgroundColor="bg.subtle">
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
          <Input borderTopRightRadius="0" borderBottomRightRadius="0" />
          <Button borderTopLeftRadius="0" borderBottomLeftRadius="0">
            Search
          </Button>
        </HStack>
      </Flex>
    </VStack>
  );
}
