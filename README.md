# HangManGame
HangmanGame-klassen är huvudklassen som utökar Application (JavaFX-biblioteket) och har metoder för att skapa och hantera olika spelstadier.

Startmetoden (start): Den sätter upp den inledande scenen med en knapp ("Spela"). När knappen klickas på, går vi vidare till nästa metod (showGameMenu).

showGameMenu-metoden: Skapar en scen för spelsidan med en "Start"-knapp. När den klickas på, går vi vidare till nästa metod (showStartScreen).

showStartScreen-metoden: Skapar en scen där användaren kan skriva in ett ord och starta spelet genom att klicka på "Starta"-knappen. När spelet startar, går vi vidare till showGameScreen.

showGameScreen-metoden: Skapar spelscenen med etiketter för det dolda ordet, antal försök, en textinmatningsruta för gissningar och en "Gissa"-knapp. Här börjar spelet.

Gissningsmetoderna (updateHiddenWord, isWordGuessed, updateGameScreen):

updateHiddenWord: Uppdaterar det dolda ordet med en gissad bokstav.
isWordGuessed: Kontrollerar om hela ordet har gissats.
updateGameScreen: Uppdaterar spelsidan med det uppdaterade dolda ordet.
showAlert-metoden: Visar ett informationselement med angiven titel och meddelande.

handleStartButtonClick-metoden: Anropas när användaren klickar på "Starta"-knappen på startsidan. Kontrollerar om inmatningen är ett giltigt ord och startar spelet om så är fallet.

handleGuessButtonClick-metoden: Anropas när användaren gissar på en bokstav. Uppdaterar spelets tillstånd baserat på gissningen och visar varningar eller gratulationsmeddelanden vid behov.

Layoutmetoderna (createVBoxWithChildren, createGridPaneWithChildren):

createVBoxWithChildren: Skapar en vertikal box (VBox) med specificerat avstånd, position och innehåll.
createGridPaneWithChildren: Skapar en rutnätslayout (GridPane) med specificerade egenskaper och innehåll.
Spelets gång:
Starta programmet.
Tryck på "Spela"-knappen för att komma till spelsidan.
Tryck på "Starta"-knappen för att skriva in ett ord och börja spelet.
På spelsidan, skriv in bokstäver i textinmatningsrutan och tryck på "Gissa"-knappen.
Upprepa steg 4 tills antingen ordet gissas korrekt eller antalet tillåtna försök tar slut.
En varningsruta visas med lämpligt meddelande baserat på spelets resultat.