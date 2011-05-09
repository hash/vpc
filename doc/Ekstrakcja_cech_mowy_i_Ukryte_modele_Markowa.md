Ukryte modele Markowa (HMM – Hidden Markov Model)
Współczesne

Przed rozpoczeciem procesu tworzenia modeli HMM należy dokonac ekstrakcji charakterystyk
(parametrów) z sygnałów mowy (np. opis sygnału mowy za pomoca współczynników LPC,
MCCC).

By wyodrębnić współczynniki LPC należy:
- zebrać 160 próbek (Zakłada się częstotliwość próbkowania 8000Hz oraz analizę każdych 20ms dźwięku. Przy takich parametrach wychodzi, że 20ms odpowiada dokładnie 160 próbkom.)
- przeflitrować próbki filtrem preemfazy (filtracja)
- wymnożyć próbki z oknem Hamminga
- wyznaczyć współczynniki filtra i wzmocnienie (mnożenie macierzy oraz autokorelacja)

- http://www.ita.wat.edu.pl/~lgrad/sieci%20neuronowe/ekstrakcja_cech_mowy.pdf
Strona 11 Opisany współczynnik LPC - opis dobry, ale całość dosyć zawikłana

- http://dariusz.banasiak.staff.iiar.pwr.wroc.pl/si/SI5301_w4.pdf
Pdf z opisem HMM

Nie będę przeklejał tekstu z pdf'ów do notepada++, bo to bzdura - jest tam trochę wzorów, które i tak by nie przeszły do notatnika.
Do dokumentacji dołączonej do projektu wytnie się tekst (najbardziej istotne zdania).