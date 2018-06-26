package com.longtailvideo.jwplayer.view.components {
	import com.longtailvideo.jwplayer.events.CaptionsEvent;
	import com.longtailvideo.jwplayer.events.CastEvent;
	import com.longtailvideo.jwplayer.events.MediaEvent;
	import com.longtailvideo.jwplayer.events.PlayerEvent;
	import com.longtailvideo.jwplayer.events.PlayerStateEvent;
	import com.longtailvideo.jwplayer.events.PlaylistEvent;
	import com.longtailvideo.jwplayer.events.ViewEvent;
    import com.longtailvideo.jwplayer.events.TrackEvent;
	import com.longtailvideo.jwplayer.model.PlaylistItem;
	import com.longtailvideo.jwplayer.parsers.SRT;
	import com.longtailvideo.jwplayer.player.IPlayer;
	import com.longtailvideo.jwplayer.player.PlayerState;
	import com.longtailvideo.jwplayer.utils.Animations;
	import com.longtailvideo.jwplayer.utils.AssetLoader;
	import com.longtailvideo.jwplayer.utils.Logger;
	import com.longtailvideo.jwplayer.utils.RootReference;
	import com.longtailvideo.jwplayer.utils.Strings;
	import com.longtailvideo.jwplayer.view.interfaces.IControlbarComponent;
	
	import flash.accessibility.AccessibilityProperties;
	import flash.display.DisplayObject;
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.events.ErrorEvent;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.events.TimerEvent;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;
	import flash.text.TextFormat;
	import flash.utils.Timer;

	/**
	 * Sent when the user interface requests that the player play the currently loaded media
	 *
	 * @eventType com.longtailvideo.jwplayer.events.ViewEvent.JWPLAYER_VIEW_PLAY
	 */
	[Event(name="jwPlayerViewPlay", type="com.longtailvideo.jwplayer.events.ViewEvent")]
	/**
	 * Sent when the user interface requests that the player pause the currently playing media
	 *
	 * @eventType com.longtailvideo.jwplayer.events.ViewEvent.JWPLAYER_VIEW_PAUSE
	 */
	[Event(name="jwPlayerViewPause", type="com.longtailvideo.jwplayer.events.ViewEvent")]
	/**
	 * Sent when the user interface requests that the player stop the currently playing media
	 *
	 * @eventType com.longtailvideo.jwplayer.events.ViewEvent.JWPLAYER_VIEW_STOP
	 */
	[Event(name="jwPlayerViewStop", type="com.longtailvideo.jwplayer.events.ViewEvent")]
	/**
	 * Sent when the user interface requests that the player play the next item in its playlist
	 *
	 * @eventType com.longtailvideo.jwplayer.events.ViewEvent.JWPLAYER_VIEW_NEXT
	 */
	[Event(name="jwPlayerViewNext", type="com.longtailvideo.jwplayer.events.ViewEvent")]
	/**
	 * Sent when the user interface requests that the player play the previous item in its playlist
	 *
	 * @eventType com.longtailvideo.jwplayer.events.ViewEvent.JWPLAYER_VIEW_PREV
	 */
	[Event(name="jwPlayerViewPrev", type="com.longtailvideo.jwplayer.events.ViewEvent")]
	/**
	 * Sent when the user interface requests that the player navigate to the playlist item's <code>link</code> property
	 *
	 * @eventType com.longtailvideo.jwplayer.events.ViewEvent.JWPLAYER_VIEW_LINK
	 */
	[Event(name="jwPlayerViewLink", type="com.longtailvideo.jwplayer.events.ViewEvent")]
	/**
	 * Sent when the user clicks the "mute" or "unmute" controlbar button
	 *
	 * @eventType com.longtailvideo.jwplayer.events.ViewEvent.JWPLAYER_VIEW_MUTE
	 */
	[Event(name="jwPlayerViewMute", type="com.longtailvideo.jwplayer.events.ViewEvent")]
	/**
	 * Sent when the user clicks the "mute" or "unmute" controlbar button
	 *
	 * @eventType com.longtailvideo.jwplayer.events.ViewEvent.JWPLAYER_VIEW_HD
	 */
	[Event(name="jwPlayerViewHD", type="com.longtailvideo.jwplayer.events.ViewEvent")]
	/**
	 * Sent when the user clicks the "fullscreen" or "end fullscreen" button
	 *
	 * @eventType com.longtailvideo.jwplayer.events.ViewEvent.JWPLAYER_VIEW_FULLSCREEN
	 */
	[Event(name="jwPlayerViewFullscreen", type="com.longtailvideo.jwplayer.events.ViewEvent")]
	/**
	 * Sent when the user clicks the volume slider
	 *
	 * @eventType com.longtailvideo.jwplayer.events.ViewEvent.JWPLAYER_VIEW_VOLUME
	 */
	[Event(name="jwPlayerViewVolume", type="com.longtailvideo.jwplayer.events.ViewEvent")]
	/**
	 * Sent when the user clicks to seek to a point in the video
	 *
	 * @eventType com.longtailvideo.jwplayer.events.ViewEvent.JWPLAYER_VIEW_SEEK
	 */
	[Event(name="jwPlayerViewSeek", type="com.longtailvideo.jwplayer.events.ViewEvent")]

	public class ControlbarComponent extends CoreComponent implements IControlbarComponent {
		protected var _buttons:Object = {};
		protected var _customButtons:Array = [];
		protected var _dividers:Array;
		protected var _defaultLayout:String = "[play prev next elapsed][time alt][duration hd cc track mute volumeH cast fullscreen]";
		protected var _defaultButtons:Array;
		protected var _currentLayout:String;
		protected var _layoutManager:ControlbarLayoutManager;
		protected var _width:Number;
		protected var _height:Number;
        protected var _timeSlider:TimeSlider;
		protected var _timeAlt:TextField;
		protected var _volSliderV:Slider;
		protected var _volSliderH:Slider;
		protected var _audioMode:Boolean = false;
		protected var _hideFullscreen:Boolean = false;
		protected var _levels:Array;
		protected var _currentQuality:Number = 0;
		protected var _hdOverlay:TooltipMenu;
		protected var _captions:Array;
		protected var _currentCaptions:Number = 0;
		protected var _ccOverlay:TooltipMenu;
		protected var _volumeOverlay:TooltipOverlay;
		protected var _lastPos:Number = 0;
		protected var _lastDur:Number = 0;
		protected var _dispWidth:Number = -1;
		protected var _mouseOverButton:Boolean = false;
		protected var _divIndex:Number = 0;
		protected var _bgColorSheet:Sprite;
        protected var _altMask:Sprite;
		protected var _liveMode:Boolean = false;
        protected var _vttLoader:AssetLoader;
		protected var animations:Animations;
		protected var _fadingOut:Number;
		protected var _instreamMode:Boolean;
		protected var  _canCast:Boolean = false;
		protected var  _casting:Boolean = false;
		protected var _currentState:String;
		protected var _tracks:Array;
		protected var _currentTrack:Number = 0;
		protected var _trackOverlay:TooltipMenu;

		public function ControlbarComponent(player:IPlayer) {
			super(player, "controlbar");

			animations = new Animations(this);
			alpha = 0;
			_instreamMode = false;
			visible = false;
			_layoutManager = new ControlbarLayoutManager(this);
			_dividers = [];
			setupBackground();
			setupDefaultButtons();
			setupOverlays();
			addEventListeners();
			updateVolumeSlider();
            updatePositionAndDurationText();
			_vttLoader = new AssetLoader();
			_vttLoader.addEventListener(Event.COMPLETE, loadComplete);
			_vttLoader.addEventListener(ErrorEvent.ERROR, loadError);
			this.addEventListener(MouseEvent.CLICK,function(evt:MouseEvent):void { evt.stopPropagation();});
		}

		public function setText(text:String=""):void {
			if ((!_timeAlt || _timeAlt.text === text) && _liveMode === !!text.length) {
				// nothing's changed
				return;
			}
			_liveMode = !!text.length;
			if (_timeAlt) {
				_timeAlt.text = text;
			}
			updateControlbarState();
		}

		private function addEventListeners():void {
			player.addEventListener(PlayerStateEvent.JWPLAYER_PLAYER_STATE, stateHandler);
			player.addEventListener(PlaylistEvent.JWPLAYER_PLAYLIST_LOADED, playlistHandler);
			player.addEventListener(PlaylistEvent.JWPLAYER_PLAYLIST_ITEM, playlistHandler);
			player.addEventListener(MediaEvent.JWPLAYER_MEDIA_MUTE, stateHandler);
			player.addEventListener(MediaEvent.JWPLAYER_MEDIA_VOLUME, updateVolumeSlider);
			player.addEventListener(MediaEvent.JWPLAYER_MEDIA_BUFFER, mediaHandler);
			player.addEventListener(MediaEvent.JWPLAYER_MEDIA_TIME, mediaHandler);
			player.addEventListener(MediaEvent.JWPLAYER_MEDIA_LEVELS, levelsHandler);
			player.addEventListener(MediaEvent.JWPLAYER_MEDIA_LEVEL_CHANGED, levelChanged);
			player.addEventListener(CaptionsEvent.JWPLAYER_CAPTIONS_LIST, captionsHandler);
			player.addEventListener(CaptionsEvent.JWPLAYER_CAPTIONS_CHANGED, captionChanged);
			player.addEventListener(TrackEvent.JWPLAYER_AUDIO_TRACKS, tracksHandler);
			player.addEventListener(TrackEvent.JWPLAYER_AUDIO_TRACK_CHANGED, trackChanged);
			player.addEventListener(CastEvent.JWPLAYER_CAST_AVAILABLE, _castAvailable);

		}


		private function _castAvailable(evt:CastEvent):void {
			_canCast = evt.available;
			updateControlbarState();
		}


		private function playlistHandler(evt:PlaylistEvent):void {
			if (!_instreamMode) {
				_liveMode = false;
				if (_timeSlider) {
					_timeSlider.removeCues();
                    setPositionAndDuration(0,0);
					_timeSlider.reset();
					var item:PlaylistItem = player.playlist.currentItem;
					var setThumbs:Boolean = false;
					if (item.tracks.length > 0) {
						for each(var track:Object in item.tracks) {
							if (!setThumbs && track.file && track.kind is String && String(track.kind).toLowerCase() == "thumbnails") {
                                _timeSlider.setThumbs(track.file);
								setThumbs = true;
							}
							if (track.file && track.kind is String && String(track.kind).toLowerCase() == "chapters") {
								loadCues(track.file);
							}
						}
					}
				}
				updateControlbarState();
			}
		}

		private function loadCues(file:String=null):void {
			_vttLoader.load(file, String);
		}

		private function loadComplete(evt:Event):void {
			var cues:Array = SRT.parseCaptions(_vttLoader.loadedObject as String, true);
			setCues(cues);
		}

		private static function loadError(evt:Event):void {
			Logger.log("error loading cues:" + evt.type);
		}

		private function get maxWidth():Number {
			return getConfigParam('maxwidth') ? Number(getConfigParam('maxwidth')) : 800;
		}

		private function stateHandler(evt:PlayerEvent=null):void {
			if (evt && evt is PlayerStateEvent) {
				_currentState = (evt as PlayerStateEvent).newstate;
			}
			updateControlbarState();
		}

		private function updateControlbarState():void {
			var newLayout:String = _defaultLayout;

			// handle _currentState
			if (_currentState == PlayerState.PLAYING) {
				newLayout = newLayout.replace('play', 'pause');
				hideButton('play');
			} else if (_currentState == PlayerState.IDLE) {
                if(_timeSlider) {
					_timeSlider.reset();
				}
                setPositionAndDuration(0,0);
				if (_player.playlist.currentItem) {
                    _lastDur = _player.playlist.currentItem.duration;
				}
				hideButton('pause');
			} else {
				hideButton('pause');
			}

			// handle playlist
			var multiList:Boolean = player.playlist.length > 1;
			var playlistShowing:Boolean = (!player.config.fullscreen && player.config.playlistposition.toLowerCase() !== "none");
			if (playlistShowing || !multiList || _instreamMode) {
				newLayout = newLayout.replace(/(prev next)/g, "");
				hideButton('prev');
				hideButton('next');
			}

			// handle isMuted
			if (isMuted) {
				newLayout = newLayout.replace("mute", "unmute");
				hideButton("mute");
			} else {
				hideButton("unmute");
			}

			// handle _casting
			if (_casting) {
				newLayout = newLayout.replace("cast", "casting");
				newLayout = newLayout.replace("fullscreen", "");
                newLayout = newLayout.replace(/hd/g, "");
                hideButton('hd');
				hideButton("cast");
				showButton("fullscreen");
				hideButton("normalscreen");
			} else {
				hideButton("casting");
			}

			// handle fullscreen
			if (player.config.fullscreen) {
				newLayout = newLayout.replace("fullscreen", "normalscreen");
				hideButton("fullscreen");
				newLayout = newLayout.replace("cast", "");
                hideButton("cast");
			} else {
				hideButton("normalscreen");
			}

			// handle no levels or instream
			if (_instreamMode || !_levels || _levels.length < 2) {
				newLayout = newLayout.replace(/hd/g, "");
				hideButton('hd');
			}

			// handle no captions
			if (!_captions || _captions.length < 2) {
				newLayout = newLayout.replace(/cc/g, "");
				hideButton('cc');
			}

			
			if (!_tracks || _tracks.length < 2) {
				newLayout = newLayout.replace(/track/g, "");
				hideButton('track');
			}
			// handle no casting or instream
			if (_instreamMode || !_canCast) {
				newLayout = newLayout.replace(/casting/g, "");
				newLayout = newLayout.replace(/cast/g, "");
				hideButton('cast');
				hideButton('casting');
			}

			// handle time slider
			if (_timeSlider) {
                _timeSlider.visible = !(_timeAlt && _timeAlt.text || _liveMode);
                if (!_timeSlider.visible) {
					showButton('alt');
					hideButton('elapsed');
					hideButton('duration');
					newLayout = newLayout.replace("duration","");
					newLayout = newLayout.replace("elapsed","");
				} else {
					hideButton('alt');
				}
			}
			//  more fullscreen
			if (_audioMode || _hideFullscreen || _player.config.fullscreen && newLayout) {
				newLayout = newLayout.replace("fullscreen", "");
				hideButton('fullscreen');
			} else {
				showButton('fullscreen');
			}
			// volume
			if (_audioMode || _instreamMode) {
				showButton('volumeH')
			} else {
				newLayout = newLayout.replace("volumeH", "");
				hideButton('volumeH');
			}

			_currentLayout = removeInactive(newLayout);
			redraw();
		}

		private function removeInactive(layout:String):String {
			var i:uint;
			if (!_defaultButtons) {
				_defaultButtons = _defaultLayout.match(/\W*([A-Za-z0-9]+?)\W/g);
				for (i = 0; i < _defaultButtons.length; i++) {
					_defaultButtons[i] = (_defaultButtons[i] as String).replace(/\W/g, "");
				}
			}
			for (i = 0; i < _defaultButtons.length; i++) {
				var button:String = _defaultButtons[i];
				if (!_buttons[button]) {
					layout = removeButtonFromLayout(button, layout);
				}
			}
			return layout;
		}

		private static function removeButtonFromLayout(button:String, layout:String):String {
			return layout.replace(button, "");
		}

		public function setInstreamMode(mode:Boolean):void {
			_instreamMode = mode;
			redraw();
		}

        private function get isLive():Boolean {
            return _lastDur <= 0 && !isDvr;
        }

        private function get isDvr():Boolean {
            return _lastDur <= -60;
        }

        private function setPositionAndDuration(position:Number, duration:Number):void {
            var min:Number = Math.min(0, duration);
            var max:Number = Math.max(0, duration);
            _lastPos = Math.min(Math.max(position, min), max);
            _lastDur = duration;
        }


		private function mediaHandler(evt:MediaEvent):void {
			switch (evt.type) {
				case MediaEvent.JWPLAYER_MEDIA_BUFFER:
				case MediaEvent.JWPLAYER_MEDIA_TIME:
                    setPositionAndDuration(evt.position, evt.duration);
                    if (isLive && evt.type == MediaEvent.JWPLAYER_MEDIA_TIME) {
						if (!_instreamMode) {
                            // In between items, the cast plugin sets duration to 0, this does not mean the video
                            //   is a live broadcast
                            var backup:String = (_casting ? "" : "Live broadcast");
							setText(player.playlist.currentItem.title || backup);
						}
					} else {
                        if (_timeSlider) {
                            if (isDvr) {
                                _timeSlider.setProgress((_lastDur - _lastPos) / _lastDur * 100);
							}
							else {
                                _timeSlider.setProgress(_lastPos / _lastDur * 100);
							}
                            _timeSlider.thumbVisible = !isLive;
							if (evt.bufferPercent > 0) {
                                var offsetPercent:Number = (evt.offset / _lastDur) * 100;
                                _timeSlider.setBuffer(evt.bufferPercent / (1-offsetPercent/100), offsetPercent);
                            }

                            _timeSlider.setDuration(_lastDur);
                            _timeSlider.live = isLive;
						}
                        if(!isLive) {
                            updatePositionAndDurationText();
						}
						if (!_instreamMode) {
							setText();
						}
					}
					break;
				default:
                    throw new Error("unknown event" + evt);
			}
		}

		private function updateVolumeSlider(event:Event=null):void {
			var sliders:Array = [_volSliderH, _volSliderV];

			for each (var volume:Slider in sliders) {
				if (!volume) { continue; }

				if (isMuted) {
					volume.setProgress(0);
					continue;
				}
				volume.setProgress(_player.config.volume);
			}

			updateControlbarState();
		}


        private function updatePositionAndDurationText():void {
            var elapsedString:String;
            var durationString:String;

            if (isSmallPlayer) {
                elapsedString = "";
                durationString = "";
            } else if(isDvr) {
                elapsedString = "-"+ Strings.digits(-_lastDur);
                durationString = "Live";
            } else if (isLive) {
                elapsedString = Strings.digits(0);
                durationString = Strings.digits(0);
            } else {
                elapsedString = Strings.digits(_lastPos);
                durationString = Strings.digits(_lastDur);
			}

			var redrawNeeded:Boolean = false;

            var elapsedTextField:TextField = getTextField('elapsed');
            var durationTextField:TextField = getTextField('duration');

            if (elapsedTextField) {
                if (elapsedString.length != elapsedTextField.text.length) {
                    redrawNeeded = true;
			}
                elapsedTextField.text = elapsedString;
			}


            if (durationTextField) {
                if (durationString.length != durationTextField.text.length) {
                    redrawNeeded = true;
                }
                durationTextField.text = durationString;
			}

            if (_timeSlider) {
                _timeSlider.setDuration(_lastDur);
                _timeSlider.live = isLive;
			}

			if (redrawNeeded) {
				redraw();
			}
		}


		private function setupBackground():void {
			var back:DisplayObject = getSkinElement("background");
			var capLeft:DisplayObject = getSkinElement("capLeft");
			var capRight:DisplayObject = getSkinElement("capRight");
			//var shade:DisplayObject = getSkinElement("shade");

			if (!back) {
				var newBackground:Sprite = new Sprite();
				newBackground.name = "background";
				newBackground.graphics.beginFill(0, 0);
				newBackground.graphics.drawRect(0, 0, 1, 1);
				newBackground.graphics.endFill();
				back = newBackground as DisplayObject;
			}

			if (!capLeft) { capLeft = new Sprite(); }
			if (!capRight) { capRight = new Sprite(); }

			_bgColorSheet = new Sprite();
			if (backgroundColor) {
				_bgColorSheet.graphics.beginFill(backgroundColor.color, 1);
				_bgColorSheet.graphics.drawRect(0, 0, 1, 1);
				_bgColorSheet.graphics.endFill();
			}
			addChildAt(_bgColorSheet, 0);


			_buttons['background'] = back;
			addChild(back);
			_height = back.height;
			player.config.pluginConfig("controlbar")['size'] = back.height;

			if (capLeft) {
				_buttons['capLeft'] = capLeft;
				addChild(capLeft);
			}

			if (capRight) {
				_buttons['capRight'] = capRight;
				addChild(capRight);
			}

		}


		private function setupDefaultButtons():void {
			addComponentButton('play', ViewEvent.JWPLAYER_VIEW_PLAY);
			addComponentButton('pause', ViewEvent.JWPLAYER_VIEW_PAUSE);
			addComponentButton('prev', ViewEvent.JWPLAYER_VIEW_PREV);
			addComponentButton('next', ViewEvent.JWPLAYER_VIEW_NEXT);
			addComponentButton('stop', ViewEvent.JWPLAYER_VIEW_STOP);
			addComponentButton('hd', null);
			addComponentButton('cc', null);
			addComponentButton('track', null);
			addComponentButton('fullscreen', ViewEvent.JWPLAYER_VIEW_FULLSCREEN, true);
			addComponentButton('normalscreen', ViewEvent.JWPLAYER_VIEW_FULLSCREEN, false);
			addComponentButton('unmute', ViewEvent.JWPLAYER_VIEW_MUTE, false);
			addComponentButton('mute', ViewEvent.JWPLAYER_VIEW_MUTE, true);
			addComponentButton('cast', ViewEvent.JWPLAYER_VIEW_CAST, true);
			addComponentButton('casting', ViewEvent.JWPLAYER_VIEW_CAST, false);
			addTextField('elapsed');
			addTextField('duration');
			addTextField('alt');
			_timeAlt = getTextField('alt');
			addSlider('time', ViewEvent.JWPLAYER_VIEW_CLICK, seekHandler);
            _timeSlider = getSlider('time') as TimeSlider;
			addSlider('volumeV', ViewEvent.JWPLAYER_VIEW_CLICK, volumeHandler, true);
			addSlider('volumeH', ViewEvent.JWPLAYER_VIEW_CLICK, volumeHandler, false);
			_volSliderV = getSlider('volumeV');
			_volSliderH = getSlider('volumeH');
			if (_buttons.hd) {
				_buttons.hd.addEventListener(MouseEvent.MOUSE_OVER, showHdOverlay);
			}
			if (_buttons.cc) {
				_buttons.cc.addEventListener(MouseEvent.MOUSE_OVER, showCcOverlay);
			}
			if (_buttons.track) {
				_buttons.track.addEventListener(MouseEvent.MOUSE_OVER, showTrackOverlay);
			}
			if (_buttons.mute && _volSliderV) {
				_buttons.mute.addEventListener(MouseEvent.MOUSE_OVER, showVolumeOverlay);
				if (_buttons.unmute) {
					_buttons.unmute.addEventListener(MouseEvent.MOUSE_OVER, showVolumeOverlay);
				}
			}
		}

		private function setupOverlays():void {
			_hdOverlay = new TooltipMenu('HD', _player.skin, hdOption);
			_hdOverlay.name = "hdOverlay";
			createOverlay(_hdOverlay, _buttons.hd);

			_ccOverlay = new TooltipMenu('CC', _player.skin, ccOption);
			_ccOverlay.name = "ccOverlay";
			createOverlay(_ccOverlay, _buttons.cc);
			
			_trackOverlay =  new TooltipMenu('TRACK', _player.skin, trackOption);
			_trackOverlay.name = "trackOverlay";
			createOverlay(_trackOverlay, _buttons.track);
			

			if (_volSliderV) {
				_volumeOverlay = new TooltipOverlay(_player.skin);
				_volumeOverlay.name = "volumeOverlay";
				_volumeOverlay.addChild(_volSliderV);
				createOverlay(_volumeOverlay, _buttons.mute);
				if(_buttons.unmute) {
					createOverlay(_volumeOverlay, _buttons.unmute);
				}
			}

		}

		private function createOverlay(overlay:TooltipOverlay, button:DisplayObject):void {
			if (button && overlay) {
				var fadeTimer:Timer = new Timer(500, 1);

				overlay.alpha = 0;
				overlay.addEventListener(MouseEvent.MOUSE_MOVE, function(evt:Event):void { fadeTimer.reset(); });
				overlay.addEventListener(MouseEvent.MOUSE_OUT, function(evt:Event):void { overlayOutHandler(fadeTimer); });
				button.addEventListener(MouseEvent.MOUSE_OUT, function(evt:Event):void { _mouseOverButton = false; fadeTimer.start(); });
				button.addEventListener(MouseEvent.MOUSE_OVER, function(evt:Event):void { fadeTimer.reset(); _mouseOverButton = true; });
				RootReference.stage.addChild(overlay);
				fadeTimer.addEventListener(TimerEvent.TIMER_COMPLETE, function(evt:Event):void { overlay.hide(); });
			}
		}

		private function overlayOutHandler(timer:Timer):void {
			timer.start();
			var buttonTimer:Timer = new Timer(200,1);
			buttonTimer.addEventListener(TimerEvent.TIMER_COMPLETE, function(evt:Event):void { overlayOutTimerHandler(timer); });
			buttonTimer.start();
		}

		private function overlayOutTimerHandler(timer:Timer):void {
			if (_mouseOverButton) {
				timer.reset();
			}
		}

		private function hdOption(level:Number):void {
			if (_levels && level >=0 && _levels.length > level) {
				_player.setCurrentQuality(level);
			}
			_hdOverlay.hide();
		}

		private function ccOption(caption:Number):void {
			if (_captions && caption >=0 && _captions.length > caption) {
				_player.setCurrentCaptions(caption);
			}
			_ccOverlay.hide();
		}
		
		private function trackOption(track:Number):void {
			if (_tracks && track >=0 && _tracks.length > track) {
				_player.setCurrentAudioTrack(track);
			}
			_trackOverlay.hide();
		}
		

		private function showHdOverlay(evt:MouseEvent):void {
			if (_audioMode) return;
			if (_hdOverlay && _levels && _levels.length > 2) _hdOverlay.show();
			hideCcOverlay();
			hideVolumeOverlay();
			hideTrackOverlay();
		}

		private function showCcOverlay(evt:MouseEvent):void {
			if (_ccOverlay && _captions && _captions.length > 2) _ccOverlay.show();
			hideHdOverlay();
			hideVolumeOverlay();
			hideTrackOverlay();
		}
		
		private function showTrackOverlay(evt:MouseEvent):void {
			if (_trackOverlay && _tracks && _tracks.length > 1) _trackOverlay.show();
			hideHdOverlay();
			hideVolumeOverlay();
			hideCcOverlay();
		}

		private function hideHdOverlay(evt:MouseEvent=null):void {
			if (_hdOverlay && !evt) {
				_hdOverlay.hide();
			}
		}

		private function hideCcOverlay(evt:MouseEvent=null):void {
			if (_ccOverlay && !evt) {
				_ccOverlay.hide();
			}
		}
		
		private function  hideTrackOverlay(evt:MouseEvent=null):void {
			if (_trackOverlay && !evt) {
				_trackOverlay.hide();
			}
		}

		private function toggleHD():void {
			if (_levels.length != 2) return;
			hdOption((_currentQuality + 1) % 2);
		}

		private function toggleCC():void {
			if (_captions.length != 2) return;
			ccOption((_currentCaptions + 1) % 2);
		}

		private function levelsHandler(evt:MediaEvent):void {
			_levels = evt.levels;
			if (_levels.length > 1) {
				_hdOverlay.clearOptions();
				for (var i:Number=0; i < _levels.length; i++) {
					_hdOverlay.addOption(_levels[i].label, i);
				}
			}
			levelChanged(evt);
		}

		private function levelChanged(evt:MediaEvent):void {
			_currentQuality = evt.currentQuality;
			var button:ComponentButton = getButton("hd") as ComponentButton;

			if (button) {
				button.setOutIcon(getSkinElement("hdButton" + (_levels.length == 2 && _currentQuality == 0 ? "Off" : "")));
			}

			if (_levels.length > 1) {
				_hdOverlay.setActive(evt.currentQuality);
			}
			updateControlbarState();
		}

		private function captionsHandler(evt:CaptionsEvent):void {
			_captions = evt.tracks;
			if (_captions.length > 1) {
				_ccOverlay.clearOptions();
				for (var i:Number=0; i < _captions.length; i++) {
					_ccOverlay.addOption(_captions[i].label, i);
				}
			}
			captionChanged(evt);
		}

		private function captionChanged(evt:CaptionsEvent):void {
			if (!_captions) return;
			var button:ComponentButton = getButton("cc") as ComponentButton;
			_currentCaptions = evt.currentTrack;

			if (button) {
				button.setOutIcon(getSkinElement("ccButton" + (_captions.length == 2 && _currentCaptions == 0 ? "Off" : "")));
			}

			if (_captions.length > 1) {
				_ccOverlay.setActive(evt.currentTrack);
			}
			updateControlbarState();
		}
		private function tracksHandler(evt:TrackEvent):void {
			_tracks = evt.tracks;
			if (_tracks.length > 1) {
				_trackOverlay.clearOptions();
				for (var j:Number=0; j < _tracks.length; j++) {
					_trackOverlay.addOption(_tracks[j].name, j);
				}
			}
			trackChanged(evt);
		}
		
		private function trackChanged(evt:TrackEvent):void {
			_currentTrack = evt.currentTrack;
			if (_tracks.length > 1) {
				_trackOverlay.setActive(evt.currentTrack);
			}
			updateControlbarState();
		}
		
		
		private function addComponentButton(name:String, event:String, eventData:*=null):void {
			var button:ComponentButton = new ComponentButton();
			button.name = name;

			// If no overIcon is defined, only the outIcon will ever appear.  This allows the CC and HD buttons to be displayed as toggles based on the logic in captionChanged and levelChanged.
			var outIcon:DisplayObject  = getSkinElement(name + "Button");
			var overIcon:DisplayObject = getSkinElement(name + "ButtonOver");

			button.setOutIcon(outIcon);
			button.setOverIcon(overIcon);

			button.clickFunction = function():void {
				if (name == "hd") {
					toggleHD();
				}
				else if (name == "cc") {
					toggleCC();
				}
				else if (event) {
					forward(new ViewEvent(event, eventData));
				}
			};

			if (outIcon || overIcon) {
				button.init();
				addButtonDisplayObject(button, name);
			}
		}


		private function addSlider(name:String, event:String, callback:Function, vertical:Boolean=true):void {
			try {
				var slider:Slider;
				if (name == "time") {
					// Time Slider
					slider = new TimeSlider(name+"Slider", _player.skin, this);
				} else {
					// Volume
					slider = new Slider('volume', _player.skin, vertical, vertical ? "tooltip" : "controlbar");
					slider.thumbVisible = true;
				}
				slider.addEventListener(event, callback);
				slider.name = name;
				slider.tabEnabled = false;
				_buttons[name] = slider;
				//_defaultLayout = removeButtonFromLayout("volume", _defaultLayout);
			} catch (e:Error) {
				Logger.log("Could not create " + name + "slider");
			}
		}


		private function addTextField(name:String):void {
			var textFormat:TextFormat = new TextFormat();

			textFormat.color = fontColor ? fontColor.color : 0xEEEEEE;

			textFormat.size = fontSize ? fontSize : 11;
			textFormat.font = "_sans";
			textFormat.bold = (!fontWeight || fontWeight == "bold");

			var textField:TextField = new TextField();
			textField.defaultTextFormat = textFormat;
			textField.selectable = false;
			textField.autoSize = TextFieldAutoSize.LEFT;
			textField.name = 'text';

			var textContainer:Sprite = new Sprite();
			textContainer.name = name;

			var skinName:String = name;
			if (name !== "alt") {
				textContainer.tabEnabled = false;
				textContainer.buttonMode = false;
			} else {
				skinName = "elapsed";
			}
			var textBackground:DisplayObject = getSkinElement(skinName+'Background');
			if (textBackground) {
				textBackground.name = 'back';
				textBackground.x = textBackground.y = 0;
				textContainer.addChild(textBackground);
				textContainer.addChild(textField);
				addChild(textContainer);
				_buttons[name] = textContainer;
			}
			if (name == "alt") {
				_altMask = new Sprite;
				addChild(_altMask);
				textContainer.mask = _altMask;
			}

		}


		private function forward(evt:ViewEvent):void {
			dispatchEvent(evt);
		}

		public function setAltMask(width:Number, height:Number):void {
			_altMask.graphics.clear();
			_altMask.graphics.beginFill( 0xffffff );
			_altMask.graphics.drawRect( 0 , 0 , width , height );
			_altMask.x = getButton('alt') ? getButton('alt').x : 0;
			_altMask.y = 0;
		}

		private function showVolumeOverlay(evt:MouseEvent):void {
			if (_audioMode || _instreamMode) return;
			if (_volumeOverlay) _volumeOverlay.show();
			hideHdOverlay();
			hideCcOverlay();
			hideTrackOverlay();
		}

		private function hideVolumeOverlay(evt:MouseEvent=null):void {
			if (_volumeOverlay && !evt) {
				_volumeOverlay.hide();
			}
		}

		private function volumeHandler(evt:ViewEvent):void {

			var volume:Number = Math.round(evt.data * 100);
			volume = volume < 10 ? 0 : volume;

			// update player.config.volume
			dispatchEvent(new ViewEvent(ViewEvent.JWPLAYER_VIEW_VOLUME, volume));

			updateVolumeSlider();
		}


		private function seekHandler(evt:ViewEvent):void {
			var duration:Number = 0;
			try {
				duration = _casting ? _lastDur : player.playlist.currentItem.duration;
			} catch (err:Error) {
			}
			var percent:Number = duration * evt.data;
			dispatchEvent(new ViewEvent(ViewEvent.JWPLAYER_VIEW_SEEK, percent));
            if(_timeSlider) {
			_timeSlider.live = (duration <= 0);
		}
        }

		private function addButtonDisplayObject(icon:ComponentButton, name:String):MovieClip {
			var acs:AccessibilityProperties = new AccessibilityProperties();
			acs.name = name;
			if (icon) {
				icon.name = name;
				_buttons[name] = icon;
				icon.accessibilityProperties = acs;
				return icon as ComponentButton;
			}
			return null;
		}



        private function showButton(name:String):void {
            hideButton(name, false);
        }

		private function hideButton(name:String, state:Boolean = true):void {
			var button:DisplayObject = _buttons[name];
			if (button && contains(button)) {
				_buttons[name].visible = !state;
				removeChild(button);
			}
		}

		public function getButton(buttonName:String):DisplayObject {
			if (buttonName === "divider") {
				if (_divIndex > _dividers.length - 1) {
					_dividers.push(getSkinElement(buttonName));
				}
				return _dividers[_divIndex++];
			} else {
				return _buttons[buttonName];
			}
		}

		public function getTextField(textName:String):TextField {
			var textContainer:Sprite = getButton(textName) as Sprite;
			if (textContainer) {
				return textContainer.getChildByName('text') as TextField;
			}
			return null;
		}


		public function getSlider(sliderName:String):Slider {
			return getButton(sliderName) as Slider;
		}


		override public function resize(width:Number, height:Number):void {
			if (getConfigParam('position') == "none") {
				visible = false;
				return;
			}

			_dispWidth = width;
			var margin:Number = getConfigParam('margin') == null ? 8 : getConfigParam('margin');
			var maxMargin:Number = (!_audioMode && maxWidth && width > maxWidth) ? (width - maxWidth) / 2 : 0;
			x = (maxMargin ? maxMargin : margin) + player.config.pluginConfig('display')['x'];
			y = height - background.height - margin + player.config.pluginConfig('display')['y'];
			_width = width - 2 * (maxMargin ? maxMargin : margin);
			_bgColorSheet.visible = false;

			var backgroundWidth:Number = _width;

			backgroundWidth -= capLeft.width;
			capLeft.x = 0;

			backgroundWidth -= capRight.width;
			capRight.x = _width - capRight.width;

			background.width = backgroundWidth;
			background.x = capLeft.width;
			setChildIndex(capLeft, numChildren - 1);
			setChildIndex(capRight, numChildren - 1);

			_bgColorSheet.width = _width;
			_bgColorSheet.height = background.height;

			if (_fullscreen != _player.config.fullscreen) {
				_fullscreen = _player.config.fullscreen;
			}

			stateHandler();
		}

        private function get isSmallPlayer():Boolean {
            return _dispWidth > 0 && _dispWidth < 320;
        }


		private function redraw():void {
            if (isSmallPlayer) {
				_currentLayout = _currentLayout.replace(/duration|elapsed/g, '');
			}
            updatePositionAndDurationText();

			clearDividers();
			addDividers();
			alignTextFields();
			_layoutManager.resize(_width, _height);

			positionOverlay(_hdOverlay, getButton('hd'));
			positionOverlay(_ccOverlay, getButton('cc'));
			positionOverlay(_trackOverlay,getButton('track'));
			positionOverlay(_volumeOverlay, isMuted ? getButton('unmute') : getButton('mute'));
		}


		private function positionOverlay(overlay:TooltipOverlay, button:DisplayObject):void {
			if (button && overlay) {
				RootReference.stage.setChildIndex(overlay, RootReference.stage.numChildren-1);
				var buttonPosition:Point = button.localToGlobal(new Point(button.width / 2, 0));
				var cbBounds:Rectangle = this.getBounds(RootReference.root);

				overlay.offsetX = 0;
				overlay.x = buttonPosition.x;
				overlay.y = cbBounds.y;

				var overlayBounds:Rectangle = overlay.getBounds(RootReference.root);
				if (overlayBounds.right >= cbBounds.right) {
					overlay.offsetX -= cbBounds.right - overlayBounds.right;
				} else if (overlayBounds.left < cbBounds.left) {
					overlay.offsetX += cbBounds.left + overlayBounds.left;
				}
			}
		}

		public function hideOverlays():void {
			hideVolumeOverlay();
			hideHdOverlay();
			hideCcOverlay();
			hideTrackOverlay();
            if (_timeSlider) {
                _timeSlider.hide();
            }
		}

		private function clearDividers():void {
			for each (var divider:DisplayObject in _dividers) {
				if (divider && divider.parent) {
					divider.parent.removeChild(divider);
				}
			}
			_divIndex = 0;
		}

		private function get isMuted():Boolean {
			return player.config.mute || (player.config.volume == 0);
		}

		
		private function addDividers():void {
			//make sure we don't add dividers a layout that already has dividers
            _currentLayout = _currentLayout.replace(/\|?\s+|\|/g, '|');
		}

		private function alignTextFields():void {
			for each(var fieldName:String in ['elapsed', 'duration']) {
				var textContainer:Sprite = _buttons[fieldName] as Sprite;
				if (!textContainer) {
					continue;
				}
				var textField:TextField = getTextField(fieldName);
				var textBackground:DisplayObject = textContainer.getChildByName('back');

				if (textField && textBackground) {
					textBackground.width = textField.textWidth + 10;
					textBackground.height = background.height;
					textField.x = (textBackground.width - textField.width) / 2;
					textField.y = (textBackground.height - textField.height) / 2;
				}
			}
		}


		public function get layout():String {
			return _currentLayout.replace(/\|/g, '<divider>');
		}

		override public function show():void {
			animations.fade(1, .5);
		}

		public function set casting(state:Boolean):void  {
			_casting = state;
		}

		public function audioMode(state:Boolean):void {
			_audioMode = state;
			stateHandler();
            if (_timeSlider) {
                _timeSlider.audioMode(state);
            }
            if (state) {
                show();
            }
		}

		public function hideFullscreen(state:Boolean):void {
			_hideFullscreen = state;
			stateHandler();
		}

		override public function hide(force:Boolean = false):void {
			if (!_audioMode) {
				animations.fade(0, .5);
				hideOverlays();
			}
		}

		private function get background():DisplayObject {
			if (_buttons['background']) {
				return _buttons['background'];
			}
			return (new Sprite());
		}


		private function get capLeft():DisplayObject {
			if (_buttons['capLeft']) {
				return _buttons['capLeft'];
			}
			return (new Sprite());
		}


		private function get capRight():DisplayObject {
			if (_buttons['capRight']) {
				return _buttons['capRight'];
			}
			return (new Sprite());
		}

		public function setCues(cues:Array):void {
            if (_timeSlider) {
                _timeSlider.setCues(cues);
            }
		}

	}
}
 
