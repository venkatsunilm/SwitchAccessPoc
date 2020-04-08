/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myapplication.nodes;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.example.myapplication.GlobalConstants;
import com.example.myapplication.SwitchAccessNodeCompat;
//import com.google.android.accessibility.switchaccess.ui.ButtonSwitchAccessIgnores;
//import com.google.android.accessibility.utils.traversal.OrderedTraversalController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Base class for tree building. Includes some common utility methods.
 */
public abstract class TreeBuilder {

    final AccessibilityService service;
    public static ArrayList<AccessibilityNodeInfoCompat> childNodes = new ArrayList<>();

    TreeBuilder(AccessibilityService service) {
        this.service = service;
    }

//  /**
//   * Adds a view hierarchy to the top of a tree.
//   *
//   * @param node The root of the view hierarchy to be added to the tree
//   * @param treeToBuildOn The tree that should be traversed if the user doesn't choose anything from
//   *     the view hierarchy
//   * @param includeNonActionableItems Whether non-actionable items should be added when building the
//   *     tree
//   * @return An updated tree that includes {@code treeToBuildOn}
//   */
//  public abstract TreeScanNode addViewHierarchyToTree(
//      SwitchAccessNodeCompat node, TreeScanNode treeToBuildOn, boolean includeNonActionableItems);

    /**
     * Obtains a list of nodes in the order TalkBack would traverse them.
     *
     * @param root The root of the tree to traverse
     * @return The nodes in {@code root}'s subtree (including root) in the order TalkBack would
     * traverse them.
     */
    public static /*LinkedList<SwitchAccessNodeCompat>*/ void getNodesInTalkBackOrder(SwitchAccessNodeCompat root) {
        // Compute windows above this one. Used for cropping node bounds. Filter out any windows
        // that have ButtonSwitchAccessIgnores as the only child of a root view. Any windows that
        // contain only these buttons should not be considered when creating the visible views.
        List<AccessibilityWindowInfo> windowsAbove = root.getWindowsAbove();
        List<AccessibilityWindowInfo> windowsAboveFiltered = new ArrayList<>();
        for (AccessibilityWindowInfo window : windowsAbove) {
            AccessibilityNodeInfo windowRoot = window.getRoot();
            if ((windowRoot != null) && (windowRoot.getChildCount() == 1)) {
                AccessibilityNodeInfo firstChild = windowRoot.getChild(0);

//                Log.i(GlobalConstants.LOGTAG, "firstChild: " + firstChild);

                if (firstChild != null) {
                    CharSequence className = firstChild.getClassName();
                    if ((className != null)
                            && ButtonSwitchAccessIgnores.class.getName().contentEquals(className)) {
                        Log.i(GlobalConstants.LOGTAG, "ButtonSwitchAccessIgnores");
                        continue;
                    }
                }
            }
            windowsAboveFiltered.add(window);
        }

        int childCount = root.getChildCount();
        Log.i(GlobalConstants.LOGTAG, "AccessibilityNodeInfoCompat childCount: " + childCount);

        AccessibilityNodeInfoCompat child = null;
        AccessibilityNodeInfoCompat secondchild = null;
        for (int i = 0; i < childCount; i++) {
            try {
                child = root.getChild(i);
                childNodes.add(child);
                Log.i(GlobalConstants.LOGTAG, "AccessibilityNodeInfoCompat child: " + i + " :" + child);

                int subChildCount = child.getChildCount();
                for (int j = 0; j < subChildCount; j++) {
                    secondchild = child.getChild(j);
                    Log.i(GlobalConstants.LOGTAG, "AccessibilityNodeInfoCompat secondchild: " + j + " :" + secondchild);
                }
//                Rect bounds = getBoundsInternal(child);
            } catch (Exception e) {

            }

            // Create our list of nodes.
            LinkedList<SwitchAccessNodeCompat> outList = new LinkedList<>();
            OrderedTraversalController traversalController = new OrderedTraversalController();
//    traversalController.initOrder(root, true);
//    AccessibilityNodeInfoCompat node = traversalController.findFirst();
//    while (node != null) {
//      // Ignore buttons that Switch Access shouldn't cache. This includes the "cancel" button
//      // as we need special handling for it during group selection (so that it is always the
//      // last item in a given branch). Ignoring it here avoids creating duplicate nodes. The
//      // menu button that appears at the top of the screen is also ignored for similar
//      // reasons.
//      CharSequence className = node.getClassName();
//      if ((className == null)
//          || !ButtonSwitchAccessIgnores.class.getName().contentEquals(className)) {
//        outList.add(new SwitchAccessNodeCompat(node.unwrap(), windowsAboveFiltered));
//      }
//      node = traversalController.findNext(node);
//    }
//    traversalController.recycle();
//    return outList;
        }
    }

//        private Rect getBoundsInternal (AccessibilityNodeInfoCompat node){
//            if (node == null) {
//                return EMPTY_RECT;
//            }
//
//            if (mCalculatingNodes.contains(node)) {
//                LogUtils.w(TAG, "node tree loop detected while calculating node bounds");
//                return EMPTY_RECT;
//            }
//
//            Rect bounds = mBoundsMap.get(node);
//            if (bounds == null) {
//                mCalculatingNodes.add(node);
//                bounds = fetchBound(node);
//                mBoundsMap.put(node, bounds);
//                mCalculatingNodes.remove(node);
//            }
//
//            return bounds;
//        }


//  /**
//   * Creates a {@link TreeScanSystemProvidedNode} if the provided {@link SwitchAccessNodeCompat} can
//   * be used to create a {@link ShowActionsMenuNode} or a {@link NonActionableItemNode}.
//   *
//   * @param compat The {@link SwitchAccessNodeCompat} that should be used to create a new node
//   * @param includeNonActionableItems Whether we should attempt to create a node if the {@link
//   *     SwitchAccessNodeCompat} corresponds to a non-actionable item
//   * @return The new node if the provided {@link SwitchAccessNodeCompat} can be used to create a
//   *     {@link ShowActionsMenuNode} or a {@link NonActionableItemNode}. Returns {@code null}
//   *     otherwise.
//   */
//  @Nullable
//  TreeScanSystemProvidedNode createNodeIfImportant(
//      SwitchAccessNodeCompat compat, boolean includeNonActionableItems) {
//    TreeScanSystemProvidedNode node = ShowActionsMenuNode.createNodeIfHasActions(service, compat);
//
//    if (node == null && includeNonActionableItems) {
//      node = NonActionableItemNode.createNodeIfHasText(compat);
//    }
//    return node;
//  }
}
