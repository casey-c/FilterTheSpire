# Filter the Spire
<a href="https://github.com/casey-c/FilterTheSpire/releases">
    <img src="https://img.shields.io/github/v/release/casey-c/FilterTheSpire?color=242634&logo=github&style=flat-square">
  </a>
  
Customize your Slay The Spire seeds! Upon starting a run, this mod will repeatedly refresh at the Neow screen until you generate a seed with your desired parameters. The parameters of your run can be adjusted in the Main Menu -> Mods -> Filter the Spire -> Config menu. Currently there's only one filter implemented so far: customizable boss swaps!

Head down to the [Installation](#installation) section below to learn how to get this mod in your game!

![Loading](github/loading.png)

![Config](github/config_small_v2.png)

## How it Works

* Upon starting a run, this mod will attempt to find a seed that fits all your specified parameters chosen in the in game config menu (shown above). The mod will continously refresh the start of the game until your settings are satisfied, giving you access to seeds that you are actually interested in playing!
* This mod attempts to have vanilla compatibility - we actually search for valid seeds rather than force them to happen manually. This means any seed you find will be shareable to be played by any PC player, with or without this mod installed!
* A small cheat is in place to guarantee four choices at Neow (usually you would need to reach at least the first boss) - the logic here is that if you have the mod installed you are probably looking to boss swap regardless of whether or not your previous run made it to the Act 1 boss
* This mod is under active development - more features and filters are planned for the near future!

## How to Use

1. Install the mod and prerequesites as described below. Launch Slay the Spire (use the "Play with Mods" option on Steam to get the ModTheSpire loader on launch). Make sure to check the box next to "Filter the Spire" to enable it. 

2. To set up your desired filters, go to Main Menu -> Mods -> Filter the Spire, and click the small "Config" button. This should bring up a menu to choose which relics you wish to find if you boss swap at Neow. This is the only filter currently working, but more should be coming within the next few weeks.

3. After setting up your desired options, you can return to the Main Menu and start a new game. You may see a brief popup screen showing how many seeds the program examined while looking for your parameters and may encounter a small wait as it searches through randomly generated seeds. After it finds one, you will be presented with an entirely vanilla game of StS that you can play as normal! If you click the boss swap option, you'll be guaranteed a boss relic that fits the filters you set up originally.


## Installation

This mod requires ModTheSpire and BaseMod, which can both be found on the Steam Workshop. To install this mod, head over to the [Releases page](https://github.com/casey-c/FilterTheSpire/releases), download the latest .jar file, and drag it into the SlayTheSpire/mods folder in your game's install directory. Eventually I'll get around to uploading it to the Steam workshop but for now you will need to check back manually for updates - sorry! (The mod upload utility is broken for my particular Linux distribution so it's been easier to just push it here to Github)

## "Automatic"-Updates

As of Alpha 0.1.3, I've now included a way for this mod to be updated through the Mod the Spire launcher screen that first shows up when you launch the game. There is a refresh button you can click that will try and determine if there is a more recent version available to download. If it finds one, you'll see a small little warning triangle next to Filter the Spire, which you can click and be prompted to download the update.

It's something you can click each time you boot up the game and then don't have to open a browser. You won't get any patch notes with this method however :(

## FAQ

* Why search for seeds and not just force in the relic? 

This is a valid question! Currently, the mod is designed to have an entirely vanilla experience - any seeds you find will be unaltered from the vanilla game and can be sent to friends to play as is (without needing this mod installed at all!). Mods that forceably swap your starter relic need to do so carefully to ensure that the swapped relic doesn't occur again as a future boss relic reward - and need to take some subtle care to avoid affecting any of the run's hidden RNG mechanics. In the future, this mod will hopefully support both possible interactions for impatient players that want to skip the wait, but for now the seeds it searches for are entirely base game friendly.

* Why hasn't it found a valid seed yet? - It's a huge number of explored seeds!

Double check to make sure the selected relics are unlocked on the profile you're using. One popular swap is Pandora's Box, which isn't unlocked at the beginning of the game (it's Silent's fourth unlock) - if you only choose relics you don't have access to, the seed finding algorithm won't be able to find you a seed you can play. In the future, I'd like it to be a bit more transparent about whether or not the parameters will be be valid. In preparation for having many more complicated filters, I've removed the upper safety limit on the number of seeds to check. It will probably just keeping going until the heat death of the universe or your pc - whichever comes first - if your parameters are incompatible or impossible (e.g. trying to get an Ironclad-only relic on a Silent run).

## Issues / Bugs / Feedback

Please drop me a line here on the [issues page](https://github.com/casey-c/FilterTheSpire/issues) if you encounter bugs, weird behavior, crashes, mod incompatibilities, etc. As the mod is under active development, you can also leave feedback, suggestions, or requests there as well. Thank you!

## Other Mods

Feel free to check out some of my other (Github exclusive) mods!

* [InfoMod](https://github.com/casey-c/spiretool-infomod) - display A20 monster AI patterns, potion chances, and other hidden aspects of the game in a user friendly set of overlays
* [BetterPaths](https://github.com/casey-c/sts_betterpaths) - highlight your map and plan your route in an all new style of path planning. Easily track which nodes you wish to travel to in the future, and which ones to avoid.
