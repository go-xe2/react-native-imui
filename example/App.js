/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, { Component } from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  KeyboardAvoidingView,
} from 'react-native';

import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';
import IMUI from 'react-native-imui';

//
// const ChatInput = IMUI.ChatInput;
// const EmojiView = IMUI.EmojiView;
// const IMInput = IMUI.IMInput;
export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }
  onSelect = (data) => {
    console.log('onSelect:', data);
  };
  onDelete =(data) => {
    console.log('onDelete:', data);
  };
  onPageChanged = (data) => {
    console.log('onPageChanged:', data);
  };
  onInputChange = (v) => {
  };
  onInputViewSizeChange = (size) => {
    if (this.state.inputLayoutHeight !== size.height) {
      this.setState({
        inputLayoutHeight: size.height,
      });
    }
  }
  componentDidMount() {
    if (this.imInputRef.current) {
      this.imInputRef.current.insertText('this is insert text');
    }
  }

  imInputRef = React.createRef();
  render () {
    const { inputLayoutHeight = 360, text = 'this is init text.' } = this.state;
    return (
          <KeyboardAvoidingView behavior={'padding'} style={{flex: 1}} >
          <View>
            <Text>this is demo</Text>
            {/*<ChatInput style={{ width: 360, height: inputLayoutHeight, backgroundColor: '#f3f3f3' }}*/}
            {/*     inputPadding={{ left: 30, top: 10, right: 10, bottom: 10 }}*/}
            {/*     galleryScale={0.6}*/}
            {/*     compressionQuality={0.6}*/}
            {/*     cameraQuality={0.7}*/}
            {/*      onSizeChange={this.onInputViewSizeChange}*/}
            {/*     customLayoutItems={{*/}
            {/*       left: ['voice'],*/}
            {/*       right: ['send'],*/}
            {/*       bottom: ['gallery', 'emoji', 'camera', 'file'],*/}
            {/*     }}*/}
            {/*     showSelectAlbumBtn*/}
            {/*     showRecordVideoBtn*/}
            {/*/>*/}
            {/*<IMInput ref={this.imInputRef} style={{ width: 300, height: 60, backgroundColor: '#f1f1f1', borderWidth: 1, borderColor: 'red', margin: 20, borderRadius: 10, padding: 10 }}*/}
            {/*         onChange={this.onInputChange}*/}
            {/*         text={text}*/}
            {/*/>*/}
            {/*<EmojiView*/}
            {/*    style={{ width: 360, height: 300, backgroundColor: 'red' }}*/}
            {/*    onSelect={this.onSelect}*/}
            {/*    onPageChanged={this.onPageChanged}*/}
            {/*    onDelete={this.onDelete}*/}
            {/*/>*/}
          </View>
          </KeyboardAvoidingView>
    );
  }
}

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
});
