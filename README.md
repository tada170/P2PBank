# P2P Bank

## Popis projektu
Tento projekt je implementací P2P (peer-to-peer) bankovní aplikace, která umožňuje vytváření a správu bankovních účtů, vkládání a výběr peněz a komunikaci mezi bankovními nody v síti. Každý node představuje jednu banku a komunikuje s ostatními prostřednictvím TCP/IP protokolu.

## Požadavky
- Java 22+
- Maven

## Instalace a spuštění
Projekt lze spustit pomocí příkazů:
- `java -jar P2Pbank.jar`

Konfigurace portu, IP adresy a timeoutů se provádí v souboru `.env`.

## Ovládání a příkazy

| Název                     | Kód | Volání                                 | Odpověď při úspěchu       | Odpověď při chybě       |
|---------------------------|-----|----------------------------------------|---------------------------|-------------------------|
| Bank code                 | BC  | BC                                     | BC `<ip>`                 | ER `<message>`          |
| Account create            | AC  | AC                                     | AC `<account>/<ip>`       | ER `<message>`          |
| Account deposit           | AD  | AD `<account>/<ip>` `<number>`         | AD                        | ER `<message>`          |
| Account withdrawal        | AW  | AW `<account>/<ip>` `<number>`         | AW                        | ER `<message>`          |
| Account balance           | AB  | AB `<account>/<ip>`                    | AB `<number>`             | ER `<message>`          |
| Account remove            | AR  | AR `<account>/<ip>`                    | AR                        | ER `<message>`          |
| Bank (total) amount       | BA  | BA                                     | BA `<number>`             | ER `<message>`          |
| Bank number of clients    | BN  | BN                                     | BN `<number>`             | ER `<message>`          |

## Použité zdroje
- [Baeldung - Thread Pool, Java and Guava](https://www.baeldung.com/thread-pool-java-and-guava)
- [Oracle - Client/Server Sockets](https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html)
- [GeeksforGeeks - Command Pattern](https://www.geeksforgeeks.org/command-pattern/)

## Znovupoužitý kód
Projekt využívá návrhový vzor **Command Pattern**.
