package com.example.demo;


import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DojoStreamTest {

    @Test
    void converterData() {
        List<Player> list = CsvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void jugadoresMayoresA35() {
        List<Player> list = CsvUtilFile.getPlayers();
        Set<Player> result = list.stream()
                .filter(jugador -> jugador.getAge() > 35)
                .collect(Collectors.toSet());
        result.forEach(System.out::println);
    }

    @Test
    void jugadoresMayoresA35SegunClub() {
        List<Player> list = CsvUtilFile.getPlayers();
        Map<String, List<Player>> result = list.stream()
                .filter(player -> player.getAge() > 35)
                .distinct()
                .collect(Collectors.groupingBy(Player::getClub));

        result.forEach((key, jugadores) -> {
            System.out.println("\n");
            System.out.println(key + ": ");
            jugadores.forEach(System.out::println);
        });

    }

    @Test
    void mejorJugadorConNacionalidadFrancia() {
        List<Player> players = CsvUtilFile.getPlayers();
        Player bestPlayer = players.stream()
                .filter(player -> player.getNational().equals("France"))
                .reduce((acum, player) -> {
                    return acum.getWinners() > player.getWinners() ? acum : player;
                })
                .get();
        System.out.println(bestPlayer);
    }


    @Test
    void clubsAgrupadosPorNacionalidad() {
        List<Player> players = CsvUtilFile.getPlayers();
        players.stream()
                .sorted(Comparator.comparing(Player::getNational))
                .flatMap(player -> {
                    Map<String, String> club = new LinkedHashMap<>();
                    club.put(player.national, player.club);
                    return club.entrySet().stream();
                })
                .forEach(System.out::println);
    }

    @Test
    void clubConElMejorJugador() {
        List<Player> players = CsvUtilFile.getPlayers();
        Player bestPlayer = players.stream()
                .reduce((acum, player) -> {
                    return acum.getWinners() > player.getWinners() ? acum : player;
                })
                .get();
        System.out.println(bestPlayer.club);

    }

    @Test
    void ElMejorJugador() {
        List<Player> players = CsvUtilFile.getPlayers();
        Player bestPlayer = players.stream()
                .reduce((acum, player) -> {
                    return acum.getWinners() > player.getWinners() ? acum : player;
                })
                .get();
        System.out.println(bestPlayer);

    }

    @Test
    void mejorJugadorSegunNacionalidad() {
        List<Player> players = CsvUtilFile.getPlayers();

//        players.stream()
//                .flatMap(player -> Stream.of(player.national))
//                .flatMap(nationality -> {
//                    HashMap<String, String> winnerFinal = new LinkedHashMap<>();
//                    Player winner = players.stream()
//                            .filter(player -> player.getNational().equals(nationality))
//                            .reduce((acum, player) -> {
//                                return acum.getWinners() > player.getWinners() ? acum : player;
//                            })
//                            .get();
//                    winnerFinal.put(nationality, winner.getName());
//                    return winnerFinal.entrySet().stream();
//                })
//                .forEach(System.out::println);

        Set<String> nationalities = players.stream()
                .flatMap(player -> Stream.of(player.national))
                .collect(Collectors.toSet());

        nationalities.forEach(nationality -> {
            Player winner = players.stream()
                    .filter(player -> player.getNational().equals(nationality))
                    .reduce((acum, player) -> {
                        return acum.getWinners() > player.getWinners() ? acum : player;
                    })
                    .get();
            System.out.println(nationality + "-" + winner.name);
        });
    }


}
