import React, { Component } from 'react';
import {
    requireNativeComponent,
    findNodeHandle,
    UIManager,
    ViewPropTypes,
    View
} from 'react-native';

import PropTypes from 'prop-types';

// const IM_EDIT_VIEW = 'im_edit_view';
const CMD_OPEN_KEYBOARD = 'openKeyboard';
const CMD_CLOSE_KEYBOARD = 'closeKeyboard';
const CMD_INSERT_TEXT = 'insertText';

const iFace = {
    name: 'RCTIMEditView',
    propTypes: {
        style: PropTypes.oneOfType([ PropTypes.object, PropTypes.array ]),
        text: PropTypes.string,
        placeholder: PropTypes.string,
        onFocus: PropTypes.func,
        onBlur: PropTypes.func,
        onChange: PropTypes.func,
    },
};

const RCTIMEditView = requireNativeComponent('RCTIMEditView', iFace, {
    nativeOnly: {
        onChange: true,
        onFocus: true,
        onBlur: true,
    }
});

console.log('RCTIMEditView:', RCTIMEditView);
//
// class IMEditView extends Component {
//     constructor(props) {
//         super(props);
//     }
//     _onFocus = (event: Event) => {
//         if (!this.props.onFocus) {
//             return;
//         }
//         const { nativeEvent } = event;
//         this.props.onFocus(nativeEvent);
//     };
//     _onBlur = (event: Event) => {
//         if (!this.props.onBlur) {
//             return;
//         }
//         const { nativeEvent } = event;
//         this.props.onBlur(nativeEvent);
//     };
//     _onChange = (event: Event) => {
//         if (!this.props.onChange) {
//             return;
//         }
//         const { nativeEvent: { text = '' } } = event;
//         this.props.onChange(text);
//     };
//     openKeyboard = () => {
//         UIManager.dispatchViewManagerCommand(findNodeHandle(this.rctRef), CMD_OPEN_KEYBOARD, null);
//     };
//     closeKeyboard = () => {
//         UIManager.dispatchViewManagerCommand(findNodeHandle(this.rctRef), CMD_CLOSE_KEYBOARD, null);
//     };
//     insertText = (text) => {
//         UIManager.dispatchViewManagerCommand(findNodeHandle(this.rctRef), CMD_INSERT_TEXT, [text])
//     };
//     render() {
//         return (
//             <View style={{ backgroundColor: 'red', width: 100, height: 100 }} />
//             // <RCTIMEditView
//             //     {...this.props}
//             //     ref={r => this.rctRef = r}
//             //     onFocus={this._onFocus}
//             //     onBlur={this._onBlur}
//             //     onChange={this._onChange}
//             // />
//         );
//     }
// }
//
// IMEditView.propTypes = {
//     ...iFace.propTypes,
// };

module.exports = RCTIMEditView;
