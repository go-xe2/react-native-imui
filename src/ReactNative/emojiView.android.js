import React, { Component } from 'react';
import {
    requireNativeComponent,
    ViewPropTypes,
} from 'react-native';
import PropTypes from 'prop-types';

const iFace = {
    name: 'RCTEmojiView',
    propTypes: {
        style: PropTypes.oneOfType([PropTypes.object, PropTypes.array]),
        onSelect: PropTypes.func,
        onDelete: PropTypes.func,
        onPageChanged: PropTypes.func,
        ...ViewPropTypes,
    },
};

const RCTEmojiView = requireNativeComponent('RCTEmojiView', iFace, {
    nativeOnly: {
        onSelect: true,
        onDelete: true,
        onPageChanged: true,
    }
});

class EmojiView extends Component {
    static propTypes = {
        ...iFace.propTypes,
    };
    constructor(props) {
        super(props);
    }
    _onSelect = (event: Event) => {
        if (!this.props.onSelect) {
            return;
        }
        const { nativeEvent: { text = '' }} = event;
        this.props.onSelect(text);
    };
    _onDelete = (event: Event) => {
        if (!this.props.onDelete) {
            return;
        }
        this.props.onDelete(event.nativeEvent);
    };
    _onPageChanged = (event: Event) => {
        if (!this.props.onPageChanged) {
            return;
        }
        const { nativeEvent: { position = 0 }} = event;
        this._onPageChanged(position);
    };

    render() {
        return (
            <RCTEmojiView
                {...this.props}
                onSelect={this._onSelect}
                onDelete={this._onDelete}
                onPageChanged={this._onPageChanged}
                />
        );
    }
};

module.exports = EmojiView;
