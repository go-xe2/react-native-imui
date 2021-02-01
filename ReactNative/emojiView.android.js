import React, { Component, PropTypes } from 'react';
import {
    View,
    requireNativeComponent,
} from 'react-native';

// const EMOJI_VIEW = 'emoji_view';

class EmojiView extends Component {
    constructor(props) {
        super(props);
    }
    _onSelectHandler = (params) => {
        if (this.props.onSelect) {
            this.props.onSelect(params);
        }
    };
    _onDeleteHandler = () => {
        if (this.props.onDelete) {
            this.props.onDelete();
        }
    };
    render() {
        return (
            <RCTEmojiView
                {...this.props}
                onSelect={this._onSelectHandler}
                onDelete={this._onDeleteHandler}
            />
        );
    }
}

EmojiView.name = 'RCTEmojiView';
EmojiView.propTypes = {
    name: 'RCTEmojiView',
    propTypes: {
        style: View.propTypes.style,
        onSelect: PropTypes.func,
        onDelete: PropTypes.func,
        onPageChanged: PropTypes.func,
        ...View.propTypes
    },
};

const RCTEmojiView = requireNativeComponent('RCTEmojiView', EmojiView, {
    nativeOnly: { onSelect: true, onDelete: true, onPageChanged: true }
});

module.exports = RCTEmojiView;
